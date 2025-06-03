package com.laskarai.hive.ui.screens.result

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.laskarai.hive.data.repository.PestDataRepository
import com.laskarai.hive.ui.components.ClickableLinkView
import com.laskarai.hive.ui.components.RecommendationCard
import com.laskarai.hive.ui.navigation.AppRoutes
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionResultScreen(
    navController: NavHostController,
    predictionLabel: String,
    imageUriString: String,
    confidence: Float
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var errorLoadingImage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(imageUriString) {
        if (imageUriString.isNotEmpty()) {
            try {
                val uri = Uri.parse(imageUriString)
                bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
                }
                errorLoadingImage = null
            } catch (e: Exception) {
                Log.e("PredictionResultScreen", "Gagal memuat bitmap dari URI: $imageUriString", e)
                errorLoadingImage = "Gagal memuat gambar."
                bitmap = null
            }
        } else {
            errorLoadingImage = "URI gambar tidak ditemukan."
        }
    }

    val pestDetails = PestDataRepository.getPestDetails(predictionLabel)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Prediksi Hama") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    navController.popBackStack(AppRoutes.CLASSIFIER_SCREEN, inclusive = false)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Selesai", color = Color.White, fontSize = 16.sp)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Menampilkan gambar yang diklasifikasi
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = "Gambar Terklasifikasi: ${pestDetails.displayName}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 10f)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 10f)
                        .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Text(errorLoadingImage ?: "Memuat gambar...")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Menampilkan nama hama dan nama ilmiahnya.
            Text(
                text = pestDetails.displayName,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            )
            if (pestDetails.scientificName != "N/A" && pestDetails.scientificName.isNotBlank()) {
                Text(
                    text = pestDetails.scientificName,
                    style = MaterialTheme.typography.titleSmall.copy(fontStyle = FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Menampilkan tingkat kepercayaan prediksi.
            Text(
                text = "Tingkat Kepercayaan: ${String.format(Locale.US, "%.1f", confidence)}%",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            // Menampilkan deskripsi hama.
            Text(
                text = "Deskripsi",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pestDetails.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )

            if (pestDetails.recommendations.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Rekomendasi Pengelolaan",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Loop melalui setiap item rekomendasi dan tampilkan menggunakan RecommendationCard
                pestDetails.recommendations.forEach { recommendation ->
                    RecommendationCard(recommendation = recommendation)
                    // Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Menampilkan catatan penting jika ada
            pestDetails.importantNotes?.let { notes ->
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Catatan Penting",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        Icons.Filled.Warning,
                        contentDescription = "Peringatan",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(end = 8.dp, top = 4.dp)
                    )
                    Text(
                        text = notes,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Menampilkan tautan eksternal jika ada
            pestDetails.externalLinks?.let { links ->
                if (links.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Informasi Lebih Lanjut",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val uriHandler = LocalUriHandler.current
                    links.forEach { (title, url) ->
                        ClickableLinkView(title = title, url = url, uriHandler = uriHandler)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}