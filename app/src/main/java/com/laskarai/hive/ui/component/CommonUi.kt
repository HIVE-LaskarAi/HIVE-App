package com.laskarai.hive.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.laskarai.hive.data.model.Recommendation

/**
 * Composable untuk menampilkan satu kartu/blok rekomendasi pengelolaan.
 * Kartu ini akan menampilkan ikon berdasarkan kategori, judul kategori, dan teks saran.
 *
 * @param recommendation Objek [Recommendation] yang berisi data untuk ditampilkan.
 */
@Composable
fun RecommendationCard(recommendation: Recommendation) {
    // Fungsi helper untuk memilih ikon berdasarkan kategori rekomendasi
    fun getIconForCategory(category: String): ImageVector {
        return when (category.lowercase()) {
            "pengendalian hayati" -> Icons.Filled.Info
            "pengendalian kimiawi" -> Icons.Filled.Info
            "pengendalian kultural" -> Icons.Filled.Info
            "monitoring & pencegahan" -> Icons.Filled.DateRange
            else -> Icons.Outlined.Info
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = getIconForCategory(recommendation.category),
                    contentDescription = recommendation.category,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = recommendation.category,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recommendation.advice,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    }
}

/**
 * Composable helper untuk menampilkan teks yang dapat diklik dan akan membuka URL.
 * (Sama seperti versi sebelumnya)
 *
 * @param title Judul tautan yang akan ditampilkan.
 * @param url URL yang akan dibuka ketika teks diklik.
 * @param uriHandler Handler untuk membuka URI
 */
@Composable
fun ClickableLinkView(
    title: String,
    url: String,
    uriHandler: UriHandler
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            Icons.Filled.Info,
            contentDescription = "Tautan Eksternal",
            modifier = Modifier.padding(end = 8.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        ClickableText(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(title)
                }
            },
            onClick = {
                try {
                    uriHandler.openUri(url)
                } catch (e: Exception) {
                    Log.e("ClickableLinkView", "Tidak dapat membuka URL: $url", e)
                }
            }
        )
    }
}