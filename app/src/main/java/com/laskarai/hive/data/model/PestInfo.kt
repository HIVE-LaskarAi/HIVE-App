package com.laskarai.hive.data.model

/**
 * Data class yang merepresentasikan satu item rekomendasi pengelolaan hama.
 *
 * @property category Kategori dari rekomendasi (misalnya, "Pengendalian Hayati", "Pengendalian Kimiawi",
 * "Pengendalian Kultural", "Monitoring & Pencegahan"). Kategori ini akan digunakan untuk
 * pengelompokan dan mungkin untuk menentukan ikon di UI.
 * @property advice Teks detail dari saran atau rekomendasi yang diberikan.
 */
data class Recommendation(
    val category: String,
    val advice: String
)

/**
 * Data class yang merepresentasikan informasi detail mengenai suatu hama,
 * termasuk rekomendasi pengelolaan yang terstruktur.
 *
 * @property displayName Nama umum hama yang mudah dikenali pengguna.
 * @property scientificName Nama ilmiah hama (jika tersedia, default ke "N/A").
 * @property description Deskripsi singkat mengenai hama, karakteristiknya, dan dampak yang ditimbulkan.
 * @property imageUrlExample URL string opsional ke gambar contoh hama tersebut.
 * @property recommendations Daftar objek [Recommendation] yang berisi saran-saran pengelolaan.
 * @property importantNotes Catatan penting atau peringatan terkait hama atau pengelolaannya.
 * @property externalLinks Peta (Map) yang berisi judul tautan sebagai kunci dan URL sebagai nilai,
 * untuk informasi lebih lanjut dari sumber eksternal.
 */
data class PestDetails(
    val displayName: String,
    val scientificName: String = "N/A",
    val description: String,
    val imageUrlExample: String? = null,
    val recommendations: List<Recommendation> = emptyList(), // Daftar rekomendasi
    val importantNotes: String? = null,
    val externalLinks: Map<String, String>? = null
)