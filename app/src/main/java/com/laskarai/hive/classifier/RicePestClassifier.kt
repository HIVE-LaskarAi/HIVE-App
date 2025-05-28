package com.laskarai.hive.classifier

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
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

        return try {
            val response: PredictionResponse = client.post("http://hive.muhsyuaib.my.id/api/predict") {
                setBody(MultiPartFormDataContent(
                    formData {
                        append("file", imageData, Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(HttpHeaders.ContentDisposition, "filename=\"image.jpg\"")
                        })
                    }
                ))
            }.body()

            Pair(response.predictedClass, response.confidence)

        } catch (e: Exception) {
            Log.e("RicePestClassifier", "Error calling API: ${e.message}", e)
            null
        }
    }

    fun close() {
        client.close()
        Log.d("RicePestClassifier", "HttpClient closed.")
    }
}