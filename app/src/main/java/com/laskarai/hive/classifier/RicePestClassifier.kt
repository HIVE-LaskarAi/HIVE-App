package com.laskarai.hive.classifier

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.laskarai.hive.data.model.ErrorResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import com.laskarai.hive.data.model.PredictionResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import java.io.ByteArrayOutputStream


class RicePestClassifier(
    private val context: Context
) {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true // dibutuhkan apabila API mengirim field tambahan
            })
        }
        expectSuccess = false // Agar Ktor tidak otomatis throw exception untuk status non-2xx sebelum diperiksa
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                val clientException = exception as? ClientRequestException
                if (clientException != null) {
                    val exceptionResponse = clientException.response
                    val exceptionResponseText = exceptionResponse.bodyAsText()
                    Log.e("RicePestClassifier", "API Client Error (${exceptionResponse.status}): $exceptionResponseText")
                }
                val serverException = exception as? ServerResponseException
                if (serverException != null) {
                    val exceptionResponse = serverException.response
                    val exceptionResponseText = exceptionResponse.bodyAsText()
                    Log.e("RicePestClassifier", "API Server Error (${exceptionResponse.status}): $exceptionResponseText")
                }
            }
        }
    }

    /**
     * Mengklasifikasikan gambar dengan mengirimkannya ke REST API.
     * Fungsi ini sekarang adalah suspend function karena operasi jaringan bersifat asinkron.
     *
     * @param bitmap Gambar yang akan diklasifikasi.
     * @return Pair yang berisi nama kelas prediksi dan confidence, atau null jika gagal.
     */
    suspend fun classify(bitmap: Bitmap): Pair<String, Float>? {
        val targetWidth: Int
        val targetHeight: Int
        val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()

        if (bitmap.width > bitmap.height) {
            targetWidth = 800
            targetHeight = (targetWidth / aspectRatio).toInt()
        } else {
            targetHeight = 800
            targetWidth = (targetHeight * aspectRatio).toInt()
        }

        val resizedBitmap = Bitmap.createScaledBitmap(
            bitmap,
            targetWidth,
            targetHeight,
            true // true untuk kualitas lebih baik
        )

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream) // Kualitas 80
        val imageData = byteArrayOutputStream.toByteArray()

        try {
            // Mengirim request dan mendapatkan HttpResponse
            val httpResponse: HttpResponse = client.post("http://hive.muhsyuaib.my.id/api/predict") {
                setBody(MultiPartFormDataContent(
                    formData {
                        append("file", imageData, Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(HttpHeaders.ContentDisposition, "filename=\"image.jpg\"")
                        })
                    }
                ))
            }

            // Memeriksa status code dari HttpResponse
            when (httpResponse.status) {
                HttpStatusCode.OK -> { // 200 OK atau Sukses
                    val predictionResponse: PredictionResponse = httpResponse.body()
                    Log.i("RicePestClassifier", "API Success: Prediksi ${predictionResponse.predictedClass} dengan confidence ${predictionResponse.confidence}")
                    return Pair(predictionResponse.predictedClass, predictionResponse.confidence)
                }
                HttpStatusCode.BadRequest -> { // 400 Bad Request
                    try {
                        val errorResponse: ErrorResponse = httpResponse.body()
                        Log.e("RicePestClassifier", "API Bad Request (400): ${errorResponse.message}")
                    } catch (e: Exception) {
                        Log.e("RicePestClassifier", "API Bad Request (400), gagal parse error body: ${httpResponse.bodyAsText()}", e)
                    }
                    return null
                }
                HttpStatusCode.InternalServerError -> { // 500 Internal Server Error
                    try {
                        val errorResponse: ErrorResponse = httpResponse.body()
                        Log.e("RicePestClassifier", "API Internal Server Error (500): ${errorResponse.message}")
                    } catch (e: Exception) {
                        Log.e("RicePestClassifier", "API Internal Server Error (500), gagal parse error body: ${httpResponse.bodyAsText()}", e)
                    }
                    return null
                }
                else -> {
                    // Untuk status code error lainnya yang tidak ditangani secara spesifik
                    val errorBodyText = httpResponse.bodyAsText()
                    Log.e("RicePestClassifier", "API Error Tidak Diketahui: Status ${httpResponse.status.value} - $errorBodyText")
                    return null
                }
            }
        } catch (e: Exception) {
            // Menangkap semua jenis exception lain
            Log.e("RicePestClassifier", "Error saat memanggil API atau memproses respons: ${e.message}", e)
            return null
        }
    }

    fun close() {
        client.close()
        Log.d("RicePestClassifier", "HttpClient closed.")
    }
}