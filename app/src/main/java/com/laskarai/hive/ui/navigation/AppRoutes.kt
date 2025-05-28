package com.laskarai.hive.ui.navigation

import android.net.Uri

/**
 * Object yang menyimpan konstanta untuk rute navigasi dalam aplikasi.
 * Ini membantu mencegah kesalahan ketik dan memudahkan pengelolaan rute.
 */
object AppRoutes {
    // Rute untuk layar utama (klasifikasi)
    const val CLASSIFIER_SCREEN = "classifier_screen"

    // Bagian dasar dari rute untuk layar hasil prediksi
    const val RESULT_SCREEN_BASE = "result_screen"

    // Nama argumen yang akan dikirimkan ke layar hasil
    const val ARG_PREDICTION_LABEL = "predictionLabel"
    const val ARG_IMAGE_URI = "imageUri"
    const val ARG_CONFIDENCE = "confidence"

    /**
     * Fungsi helper untuk membuat rute lengkap ke layar hasil prediksi
     * dengan menyertakan argumen yang diperlukan.
     *
     * @param label Label prediksi hama.
     * @param imageUri String URI dari gambar yang diklasifikasi.
     * @param confidence Tingkat kepercayaan prediksi (float).
     * @return String rute yang siap digunakan untuk navigasi.
     */
    fun resultScreenWithArgs(label: String, imageUri: String, confidence: Float): String {
        // URI perlu di-encode agar aman dilewatkan sebagai argumen dalam URL/rute.
        val encodedUri = Uri.encode(imageUri)
        return "$RESULT_SCREEN_BASE/$label/$encodedUri/$confidence"
    }
}