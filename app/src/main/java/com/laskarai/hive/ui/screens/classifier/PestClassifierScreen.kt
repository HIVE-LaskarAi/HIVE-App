package com.laskarai.hive.ui.screens.classifier

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.laskarai.hive.classifier.RicePestClassifier
import com.laskarai.hive.ui.navigation.AppRoutes
import com.laskarai.hive.utils.ComposeFileProvider

/**
 * Layar utama aplikasi tempat pengguna dapat memilih gambar dari galeri atau
 * mengambil foto baru menggunakan kamera untuk diidentifikasi jenis hamanya.
 *
 * @param classifier Instance dari [RicePestClassifier] untuk melakukan klasifikasi gambar.
 * @param navController Controller untuk menangani navigasi ke layar lain (misalnya, layar hasil).
 */
@Composable
fun PestClassifierScreen(
    classifier: RicePestClassifier,
    navController: NavHostController
) {
    // State untuk menyimpan URI gambar yang dipilih (baik dari galeri maupun kamera).
    var sourceImageUri by remember { mutableStateOf<Uri?>(null) }
    // State untuk menyimpan Bitmap dari gambar yang telah dipilih dan di-decode.
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    // State untuk menandakan apakah proses loading (gambar atau klasifikasi) sedang berlangsung.
    var isLoading by remember { mutableStateOf(false) } // Menggabungkan isLoadingBitmap dan isLoadingClassification
    // State untuk menampilkan pesan status (info, error, loading).
    var statusMessage by remember { mutableStateOf<String?>(null) }

    val context: Context = LocalContext.current
    // State untuk menyimpan URI sementara saat mengambil gambar dari kamera.
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    /// Fungsi untuk mereset semua state yang berhubungan dengan gambar dan prediksi
    fun resetImageStates() {
        sourceImageUri = null
        selectedBitmap = null
        isLoading = false
        statusMessage = null
    }

    // Launcher untuk memilih gambar dari galeri.
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            resetImageStates()
            sourceImageUri = uri
        }
    }

    // Launcher untuk mengambil foto dengan kamera.
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            tempCameraUri?.let { uri ->
                resetImageStates()
                sourceImageUri = uri // Memicu LaunchedEffect untuk memuat bitmap
            }
        } else {
            Log.d("PestClassifierScreen", "Pengambilan foto dibatalkan atau gagal.")
            statusMessage = "Pengambilan foto gagal."
            isLoading = false
        }
        tempCameraUri = null // Reset URI sementara setelah digunakan
    }

    // Launcher untuk meminta izin penggunaan kamera.
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Izin diberikan, buat URI dan luncurkan kamera
            tempCameraUri = ComposeFileProvider.getImageUri(context)
            tempCameraUri?.let { uri -> cameraLauncher.launch(uri) }
        } else {
            Log.d("PestClassifierScreen", "Izin kamera ditolak oleh pengguna.")
            statusMessage = "Izin kamera ditolak."
            isLoading = false
        }
    }

    // LaunchedEffect akan dijalankan ketika `sourceImageUri` berubah.
    // Digunakan untuk memuat Bitmap dari URI gambar yang dipilih.
    LaunchedEffect(sourceImageUri) {
        sourceImageUri?.let { uri ->
            isLoading = true
            statusMessage = "Memproses gambar..."
            selectedBitmap = null // Hapus bitmap sebelumnya
            try {
                val bitmapResult = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
                selectedBitmap = bitmapResult.copy(Bitmap.Config.ARGB_8888, true)
                statusMessage = null // Hapus pesan status jika berhasil
            } catch (e: Exception) {
                Log.e("PestClassifierScreen", "Error memuat bitmap dari URI: $uri", e)
                selectedBitmap = null
                statusMessage = "Gagal memuat gambar. Coba gambar lain."
            } finally {
                if (statusMessage != "Mengklasifikasi...") {
                    isLoading = false
                }
            }
        }
    }

    /**
     * Fungsi untuk melakukan prediksi pada `selectedBitmap` dan kemudian
     * menavigasi ke layar hasil jika prediksi berhasil.
     */
    fun performPredictionAndNavigate() {
        selectedBitmap?.let { bitmap ->
            isLoading = true
            statusMessage = "Mengklasifikasi..."
            try {
                val (label, probabilities) = classifier.classify(bitmap)
                val confidence = probabilities.maxOrNull() ?: 0.0f
                Log.d("PestClassifierScreen", "Prediksi: $label, Kepercayaan: $confidence")

                sourceImageUri?.let { uri ->
                    navController.navigate(
                        AppRoutes.resultScreenWithArgs(
                            label = label,
                            imageUri = uri.toString(),
                            confidence = confidence
                        )
                    )
                    // Reset state di layar ini setelah navigasi berhasil
                    statusMessage = null
                } ?: run {
                    statusMessage = "URI gambar tidak tersedia untuk navigasi."
                }

            } catch (e: Exception) {
                Log.e("PestClassifierScreen", "Error selama klasifikasi", e)
                statusMessage = "Gagal melakukan klasifikasi pada gambar."
            } finally {
                isLoading = false
            }
        } ?: run {
            statusMessage = "Tidak ada gambar dipilih untuk prediksi."
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Pengenal Hama Padi", // Judul aplikasi
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        // Area untuk menampilkan gambar yang dipilih atau pesan status
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 12.dp)
                .background(
                    if (selectedBitmap == null && statusMessage == null) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    else Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading && statusMessage == "Memproses gambar...") {
                CircularProgressIndicator()
                Text("Memproses gambar...", Modifier.padding(top = 60.dp))
            } else if (selectedBitmap != null) {
                Image(
                    bitmap = selectedBitmap!!.asImageBitmap(),
                    contentDescription = "Gambar Terpilih",
                    modifier = Modifier.fillMaxSize()
                )
            } else if (statusMessage != null && statusMessage != "Mengklasifikasi...") {
                // Tampilkan pesan status lain jika tidak ada gambar dan tidak sedang klasifikasi
                Text(
                    text = statusMessage!!,
                    color = if (statusMessage!!.startsWith("Gagal") || statusMessage!!.startsWith("Error")) MaterialTheme.colorScheme.error else LocalContentColor.current,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Text(
                    "Pilih gambar atau ambil foto untuk memulai",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = "Identifikasi hama padi dengan mengunggah gambar atau mengambil foto.",
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        // Tombol untuk memilih gambar dari galeri
        Button(
            onClick = {
                resetImageStates()
                galleryLauncher.launch("image/*")
            },
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Ambil dari Galeri", color = Color.Black)
        }

        // Tombol untuk mengambil foto dari kamera
        Button(
            onClick = {
                resetImageStates()
                // Cek izin kamera
                when (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
                    PackageManager.PERMISSION_GRANTED -> {
                        // Izin sudah ada, buat URI dan luncurkan kamera
                        tempCameraUri = ComposeFileProvider.getImageUri(context)
                        tempCameraUri?.let { uri -> cameraLauncher.launch(uri) }
                    }
                    else -> {
                        // Izin belum ada, minta izin
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Ambil Foto dari Kamera", color = Color.Black)
        }

        // Tombol untuk melakukan prediksi
        Button(
            onClick = {
                performPredictionAndNavigate()
            },
            // Tombol aktif jika ada bitmap terpilih dan tidak sedang loading
            enabled = selectedBitmap != null && !isLoading,
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                disabledContainerColor = Color(0xff51b855).copy(alpha = 0.5f),
                disabledContentColor = Color.Gray.copy(alpha = 0.7f),
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isLoading && statusMessage == "Mengklasifikasi...") {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp, color = Color.Black)
            } else {
                Text("Prediksi Hama", color = Color.White)
            }
        }

        // Tampilkan pesan status klasifikasi atau error di bawah tombol jika relevan
        if (statusMessage != null && (statusMessage == "Mengklasifikasi..." || (statusMessage!!.startsWith("Gagal melakukan klasifikasi") || statusMessage!!.startsWith("URI gambar tidak tersedia")))) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = statusMessage!!,
                color = if (statusMessage!!.startsWith("Gagal") || statusMessage!!.startsWith("URI")) MaterialTheme.colorScheme.error else LocalContentColor.current,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}