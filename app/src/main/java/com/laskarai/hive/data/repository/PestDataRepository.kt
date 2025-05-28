package com.laskarai.hive.data.repository

import com.laskarai.hive.data.model.PestDetails
import com.laskarai.hive.data.model.Recommendation

/**
 * Object Repository yang menyediakan data detail mengenai hama.
 * Untuk saat ini, data disimpan secara lokal dalam bentuk Map (hardcoded).
 */
object PestDataRepository {
    private val pestInfoMap = mapOf(
        "rice_leaf_caterpillar" to PestDetails(
            displayName = "Penggerek Batang Padi",
            scientificName = "Chilo suppressalis (umum)",
            description = "Larva penggerek batang padi melubangi batang padi, menyebabkan gejala 'sundep' pada tanaman muda atau 'beluk' pada malai yang matang, yang mengakibatkan kehilangan hasil yang signifikan.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Beberapa spesies yang agresif dari tawon Cotesia ruficrus dan Eupteromalus parnarae telah berhasil diterapkan di lahan. Serangga ini bertelur di larva penggerek batang, membunuh mereka perlahan. \n\n" +
                            "Metode kebiasaan tanam yang penting termasuk peningkatan permukaan air ketika populasi berada dalam tahap kepompong untuk menenggelamkan mereka. Penggenangan juga membatasi penyebaran larva dari tanaman ke tanaman. \n\n" +
                            "Bebek di sawah juga membantu mengendalikan populasi penggerek batang."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Pertimbangkan selalu pendekatan terpadu berupa tindakan pencegahan bersama dengan perlakuan hayati jika tersedia. Untuk mencegah ulat bergerak ke lahan lain, berikan semprotan sepanjang batas lahan yang terserang dengan sipermetrin untuk menghindari migrasi larva.\n\n" +
                            "Jika ada serangan ulat grayak yang tinggi (mungkin maksudnya penggerek batang), semprotan kimia diperlukan. Disarankan untuk menyemprotkan sipermetrin @ 1 ml / 1 ltr air. Waktu terbaik untuk menyemprot adalah di akhir hari."
                ),
                Recommendation(
                    category = "Pengendalian Kultural",
                    advice = "Tanam varietas padi yang tahan terhadap penggerek batang jika tersedia di wilayah Anda."
                ),
                Recommendation(
                    category = "Monitoring & Pencegahan",
                    advice = "Lakukan pemantauan rutin di lapangan untuk mendeteksi keberadaan telur, larva, atau gejala serangan sejak dini."
                )
            ),
            importantNotes = "Pengendalian kimia harus menjadi pilihan terakhir dan dilakukan dengan hati-hati. Selalu ikuti instruksi pada label produk, gunakan alat pelindung diri, dan pertimbangkan dampaknya terhadap lingkungan. Konsultasikan dengan petugas penyuluh pertanian setempat untuk rekomendasi dan peraturan terbaru.",
            externalLinks = mapOf(
                "Info Penggerek Batang - IRRI" to "http://www.knowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/stem-borer",
                "Balai Penelitian Tanaman Padi (Contoh)" to "http://balitpa.litbang.pertanian.go.id/"
            )
        ),
        "Brown Planthopper" to PestDetails(
            displayName = "Wereng Batang Coklat",
            scientificName = "Nilaparvata lugens",
            description = "Wereng Batang Coklat (WBC) menghisap cairan dari pangkal batang padi, menyebabkan tanaman menguning dan kering ('hopperburn'). WBC juga dapat menularkan penyakit virus.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Konservasi musuh alami seperti laba-laba, kepik mirid, dan kumbang predator."
                ),
                Recommendation(
                    category = "Pengendalian Kultural",
                    advice = "Gunakan varietas padi yang tahan WBC."
                ),
                Recommendation(
                    category = "Pengendalian Kultural",
                    advice = "Terapkan pengairan berselang (Intermittent Irrigation/AWD) karena genangan terus menerus dapat mendukung perkembangan WBC."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Hindari penggunaan insektisida spektrum luas yang dapat membunuh musuh alami. Gunakan insektisida selektif jika populasi WBC mencapai ambang batas ekonomi."
                )
            ),
            importantNotes = "Pemupukan Nitrogen (N) yang berlebihan dapat meningkatkan populasi WBC. Perhatikan dosis pemupukan.",
            externalLinks = mapOf(
                "Info WBC - IRRI" to "http://www.knowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/brown-planthopper-bph"
            )
        ),
    )

    /**
     * Fungsi untuk mengambil detail informasi hama berdasarkan label prediksi dari model.
     *
     * @param label String label output dari model TFLite.
     * @return [PestDetails] yang sesuai dengan label. Jika label tidak ditemukan,
     * akan mengembalikan [PestDetails] default dengan pesan informasi tidak tersedia.
     */
    fun getPestDetails(label: String): PestDetails {
        return pestInfoMap[label] ?: PestDetails(
            displayName = label, // Jika tidak ada, tampilkan label mentah sebagai nama
            scientificName = "N/A",
            description = "Informasi detail untuk hama ini belum tersedia dalam basis data aplikasi. Silakan konsultasikan dengan ahli pertanian setempat.",
            recommendations = listOf(
                Recommendation(
                    category = "Saran Umum",
                    advice = "Pastikan identifikasi hama sudah benar. Untuk pengelolaan lebih lanjut, sangat disarankan untuk berkonsultasi dengan petugas penyuluh pertanian di daerah Anda atau ahli hama tanaman."
                )
            ),
            importantNotes = "Data untuk hama '$label' tidak lengkap. Informasi yang ditampilkan mungkin terbatas."
        )
    }
}