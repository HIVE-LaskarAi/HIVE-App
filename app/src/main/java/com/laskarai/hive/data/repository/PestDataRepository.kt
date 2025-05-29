package com.laskarai.hive.data.repository

import com.laskarai.hive.data.model.PestDetails
import com.laskarai.hive.data.model.Recommendation

/**
 * Object Repository yang menyediakan data detail mengenai hama.
 * Untuk saat ini, data disimpan secara lokal dalam bentuk Map (hardcoded).
 */
object PestDataRepository {
    private val pestInfoMap = mapOf(
        "asiatic_rice_borer" to PestDetails(
            displayName = "Penggerek Batang Padi",
            scientificName = "Chilo suppressalis (umum)",
            description = "Larva penggerek batang padi melubangi batang padi, menyebabkan gejala 'sundep' pada tanaman muda atau 'beluk' pada malai yang matang, yang mengakibatkan kehilangan hasil yang signifikan.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Melepaskan tawon parasitoid Paratheresia claripalpis dan Eriborus sinicus adalah cara yang efektif untuk membatasi perkembangan populasi dan kerusakan akibat hama di beberapa negara. Pemangsa lainnya termasuk beberapa spesies laba-laba."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Selalu pertimbangkan pendekatan terpadu berupa tindakan pencegahan bersama dengan perlakuan hayati jika tersedia. Jika insektisida dibutuhkan, semprotkan produk yang mengandung klorantraniliprol. Penebaran butiran insektisida saat tanam dan selama pertumbuhan dapat mengurangi infeksi. Gejala hama harus didiagnosis sejak awal, kecuali jika tanaman tidak layak dilindungi."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Lakukan pemantauan rutin di lapangan untuk mendeteksi keberadaan telur, larva, atau gejala serangan sejak dini."
                )
            ),
            importantNotes = "Pengendalian kimia harus menjadi pilihan terakhir dan dilakukan dengan hati-hati. Selalu ikuti instruksi pada label produk, gunakan alat pelindung diri, dan pertimbangkan dampaknya terhadap lingkungan. Konsultasikan dengan petugas penyuluh pertanian setempat untuk rekomendasi dan peraturan terbaru.",
            externalLinks = mapOf(
                "Info Penggerek Batang - IRRI" to "http://www.knowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/stem-borer",
                "Balai Penelitian Tanaman Padi (Contoh)" to "http://balitpa.litbang.pertanian.go.id/"
            )
        ),
        "brown_plant_hopper" to PestDetails(
            displayName = "Wereng Batang Coklat",
            scientificName = "Nilaparvata lugens",
            description = "Wereng Batang Coklat (WBC) menghisap cairan dari pelepah daun dan batang padi, menyebabkan tanaman menguning, kering (hopperburn), dan bahkan mati. WBC juga merupakan vektor virus kerdil hampa dan kerdil rumput.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Manfaatkan musuh alami seperti laba-laba (misalnya Lycosa pseudoannulata), kumbang kepik (Paederus fuscipes), dan parasitoid telur (misalnya Anagrus spp.). Hindari penggunaan insektisida spektrum luas yang dapat membunuh musuh alami."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Gunakan insektisida secara bijaksana dan hanya jika populasi WBC melebihi ambang batas ekonomi. Pilih insektisida yang selektif dan efektif terhadap WBC, seperti yang mengandung bahan aktif buprofezin, imidakloprid, atau pymetrozine. Rotasi bahan aktif penting untuk mencegah resistensi."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Tanam varietas padi yang tahan terhadap WBC. Lakukan penanaman serentak dan rotasi tanaman. Sanitasi lingkungan sawah dengan membersihkan gulma dan sisa tanaman yang dapat menjadi inang alternatif."
                )
            ),
            importantNotes = "Pemantauan rutin populasi WBC sangat penting. Pengendalian kimiawi harus didasarkan pada ambang batas ekonomi dan dilakukan dengan memperhatikan dampaknya terhadap musuh alami dan lingkungan. Konsultasikan dengan PPL setempat.",
            externalLinks = mapOf(
                "Wereng Batang Coklat - IRRI" to "https://www.riceknowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/brown-planthopper",
                "Pengendalian WBC - Kementerian Pertanian RI (Contoh)" to "https://tanamanpangan.pertanian.go.id/index.php/berita/46-program" // Ganti dengan link yang lebih spesifik jika ada
            )
        ),
        "paddy_stem_maggot" to PestDetails(
            displayName = "Lalat Pucuk Padi",
            scientificName = "Atherigona oryzae",
            description = "Larva lalat pucuk padi menyerang titik tumbuh tanaman padi muda, menyebabkan pucuk tanaman mati dan mengering, mirip gejala 'sundep' tetapi tanpa lubang gerekan pada batang. Serangan biasanya terjadi pada fase vegetatif awal.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Beberapa jenis musuh alami seperti parasitoid larva dan pupa dapat membantu mengendalikan populasi. Praktik budidaya yang baik dapat mendukung keberadaan musuh alami."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Aplikasi insektisida dapat dilakukan jika serangan berat, terutama pada persemaian atau tanaman muda. Insektisida sistemik atau kontak dengan bahan aktif seperti fipronil atau karbofuran (jika masih diizinkan dan sesuai rekomendasi) dapat dipertimbangkan. Selalu perhatikan dosis dan waktu aplikasi."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Tanam tepat waktu untuk menghindari puncak populasi lalat. Jaga kebersihan persemaian dan area pertanaman dari sisa-sisa tanaman yang terinfeksi. Penggunaan varietas yang memiliki pertumbuhan awal cepat dapat mengurangi risiko kerusakan."
                )
            ),
            importantNotes = "Identifikasi yang akurat penting untuk membedakan gejala serangan lalat pucuk dengan penggerek batang. Pengendalian kimiawi sebaiknya dilakukan berdasarkan tingkat serangan dan rekomendasi setempat.",
            externalLinks = mapOf(
                "Paddy Stem Maggot - ICRISAT (Contoh General)" to "https://www.icrisat.org/sorghum-pests-and-diseases/paddy-stem-maggot/", // Cari sumber yang lebih spesifik untuk padi jika ada
                "Info Hama Tanaman Pangan - Deptan (Contoh)" to "http://ditjenpp.pertanian.go.id/index.php/direktorat-perlindungan-tanaman-pangan"
            )
        ),
        "rice_gall_midge" to PestDetails(
            displayName = "Ganjur Padi",
            scientificName = "Orseolia oryzae",
            description = "Larva ganjur menyebabkan pembengkakan pada dasar anakan padi yang disebut 'puru' atau 'silver shoot', yang menghambat pembentukan malai dan mengakibatkan kehilangan hasil. Serangan biasanya terjadi pada fase vegetatif.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Beberapa spesies tawon parasitoid seperti Platygaster oryzae dan Neanastatus grallarius efektif dalam mengendalikan populasi ganjur. Pemeliharaan musuh alami penting."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Aplikasi insektisida butiran sistemik pada saat tanam atau pada awal serangan dapat efektif. Insektisida dengan bahan aktif fipronil atau klorantraniliprol dapat dipertimbangkan. Penyemprotan mungkin kurang efektif karena larva berada di dalam puru."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Tanam varietas tahan ganjur. Lakukan penanaman serentak dan atur waktu tanam untuk menghindari puncak populasi hama. Sanitasi lingkungan dan menghilangkan tanaman inang alternatif seperti rumput liar."
                )
            ),
            importantNotes = "Pemantauan gejala puru sejak dini sangat krusial. Penggunaan varietas tahan adalah strategi pencegahan terbaik. Konsultasikan dengan ahli pertanian untuk pemilihan insektisida yang tepat.",
            externalLinks = mapOf(
                "Rice Gall Midge - IRRI" to "https://www.riceknowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/gall-midge",
                "Pengendalian Ganjur - Balitbangtan (Contoh)" to "https://www.litbang.pertanian.go.id/" // Cari publikasi spesifik
            )
        ),
        "rice_leaf_caterpillar" to PestDetails(
            displayName = "Ulat Grayak Padi",
            scientificName = "Spodoptera spp. (misalnya Spodoptera mauritia, Spodoptera frugiperda)",
            description = "Larva ulat grayak memakan daun padi, seringkali mulai dari pinggir daun. Serangan berat dapat menyebabkan daun habis dan tinggal tulang daunnya saja (defoliasi), yang mengganggu fotosintesis dan pertumbuhan tanaman.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Manfaatkan musuh alami seperti virus NPV (Nuclear Polyhedrosis Virus) yang spesifik untuk Spodoptera, bakteri Bacillus thuringiensis (Bt), serta predator seperti burung dan beberapa jenis serangga."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Gunakan insektisida jika populasi ulat tinggi dan kerusakan mencapai ambang batas. Pilih insektisida yang efektif dan lebih selektif seperti yang berbahan aktif emamektin benzoat, klorantraniliprol, atau spinosad. Lakukan penyemprotan pada sore atau pagi hari saat ulat aktif."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Lakukan pemantauan rutin untuk deteksi dini telur dan larva. Jaga kebersihan sawah dari gulma yang bisa menjadi tempat persembunyian. Pengolahan tanah yang baik dapat membantu membunuh pupa dalam tanah."
                )
            ),
            importantNotes = "Identifikasi jenis Spodoptera penting karena beberapa spesies mungkin memiliki tingkat resistensi yang berbeda terhadap insektisida. Pertimbangkan penggunaan biopestisida sebagai alternatif yang lebih ramah lingkungan.",
            externalLinks = mapOf(
                "Armyworms in Rice - IRRI" to "https://www.riceknowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/armyworms",
                "Info Spodoptera frugiperda - FAO" to "http://www.fao.org/fall-armyworm/en/"
            )
        ),
        "rice_leaf_hopper" to PestDetails(
            displayName = "Wereng Daun Padi",
            scientificName = "Nephotettix spp. (misalnya Nephotettix virescens, Nephotettix nigropictus)",
            description = "Wereng daun menghisap cairan dari daun padi, menyebabkan daun menguning dan pertumbuhan tanaman terhambat. Lebih penting lagi, wereng daun adalah vektor utama penyakit virus tungro yang sangat merusak.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Musuh alami seperti laba-laba, kumbang kepik, dan parasitoid telur (misalnya Oligosita spp.) dapat membantu menekan populasi wereng daun. Hindari insektisida spektrum luas."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Pengendalian kimiawi lebih ditujukan untuk mencegah penyebaran virus tungro. Gunakan insektisida sistemik atau kontak jika populasi wereng tinggi atau jika ada indikasi penyakit tungro. Bahan aktif seperti imidakloprid, tiametoksam, atau buprofezin bisa dipertimbangkan."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Tanam varietas tahan wereng dan tahan tungro. Lakukan penanaman serentak dan rotasi tanaman. Sanitasi lingkungan sawah, termasuk membersihkan gulma yang menjadi inang wereng. Eradikasi tanaman yang terinfeksi tungro."
                )
            ),
            importantNotes = "Fokus utama pengendalian wereng daun adalah manajemen penyakit tungro. Pemantauan rutin dan tindakan cepat sangat diperlukan. Konsultasikan dengan PPL untuk strategi pengendalian terpadu.",
            externalLinks = mapOf(
                "Green Leafhopper - IRRI" to "https://www.riceknowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/green-leafhopper",
                "Penyakit Tungro - Kementerian Pertanian RI (Contoh)" to "https://www.pertanian.go.id/" // Cari link spesifik
            )
        ),
        "rice_leaf_roller" to PestDetails(
            displayName = "Hama Putih Palsu / Penggulung Daun Padi",
            scientificName = "Cnaphalocrocis medinalis",
            description = "Larva hama ini menggulung daun padi secara memanjang dan memakan jaringan hijau (klorofil) dari dalam gulungan daun, meninggalkan bekas goresan putih transparan. Serangan berat dapat mengurangi area fotosintesis.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Berbagai musuh alami seperti tawon parasitoid (Trichogramma spp., Bracon spp.), laba-laba, dan kepik dapat memangsa larva dan telur. Penggunaan insektisida selektif penting untuk menjaga populasi musuh alami."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Insektisida digunakan jika lebih dari 25% daun menunjukkan gejala kerusakan pada fase anakan atau 10% pada fase pembungaan. Insektisida kontak atau sistemik seperti fipronil, klorantraniliprol, atau emamektin benzoat bisa efektif. Penyemprotan diarahkan ke bagian atas tanaman."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Pemupukan berimbang, hindari penggunaan nitrogen berlebihan yang membuat daun sukulen dan disukai hama. Sanitasi gulma di sekitar sawah. Penggunaan varietas yang agak tahan jika tersedia."
                )
            ),
            importantNotes = "Kerusakan ringan seringkali dapat ditoleransi oleh tanaman tanpa penurunan hasil yang signifikan. Pengendalian kimiawi harus berdasarkan ambang batas kerusakan. Hindari penyemprotan yang tidak perlu.",
            externalLinks = mapOf(
                "Rice Leaffolder - IRRI" to "https://www.riceknowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/leaffolder",
                "Pengendalian Hama Putih Palsu - Balitkabi (Contoh)" to "https://balitkabi.litbang.pertanian.go.id/" // Cari artikel terkait
            )
        ),
        "rice_shell_pest" to PestDetails(
            displayName = "Keong Mas",
            scientificName = "Pomacea canaliculata",
            description = "Keong mas memakan bibit padi muda dan tanaman padi muda, menyebabkan kerusakan signifikan terutama pada 15-30 hari setelah tanam (HST). Mereka dapat menghabiskan petak sawah dengan cepat jika populasinya tinggi.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Mekanis/Fisik",
                    advice = "Pengambilan keong dan telurnya secara manual. Pemasangan saringan pada saluran masuk air untuk mencegah masuknya keong. Penggunaan tongkat bambu untuk mengumpulkan telur. Pengaturan ketinggian air agar bibit muda tidak terjangkau."
                ),
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Pemanfaatan predator alami seperti bebek atau itik yang digembalakan di sawah sebelum tanam atau setelah panen. Beberapa jenis ikan juga dapat memakan keong muda."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Gunakan moluskisida berbahan aktif niklosamida atau metaldehida secara hati-hati dan sesuai dosis anjuran. Aplikasi dilakukan pada area yang tergenang air dimana keong aktif. Pertimbangkan dampaknya terhadap organisme non-target."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Persiapan lahan yang baik. Tanam bibit yang sudah cukup umur (lebih dari 21 hari). Membuat caren (parit kecil) di pinggir sawah sebagai tempat berkumpulnya keong sehingga mudah dikumpulkan."
                )
            ),
            importantNotes = "Pengendalian keong mas paling efektif dengan pendekatan terpadu. Pengendalian kimiawi harus menjadi pilihan terakhir karena potensi dampaknya terhadap lingkungan perairan. Selalu ikuti petunjuk pada label produk.",
            externalLinks = mapOf(
                "Golden Apple Snail - IRRI" to "https://www.riceknowledgebank.irri.org/training/fact-sheets/pest-management/molluscs/item/golden-apple-snail",
                "Pengendalian Keong Mas - Deptan (Contoh)" to "http://ditjenpp.pertanian.go.id/index.php/berita/konten/opt-tanaman-padi-keong-mas-pomacea-canaliculata"
            )
        ),
        "rice_stem_fly" to PestDetails(
            displayName = "Lalat Bibit Padi",
            scientificName = "Hydrellia philippina (sering juga merujuk pada Atherigona oryzae untuk 'stem maggot')",
            description = "Larva lalat bibit (Hydrellia spp.) merusak tanaman padi muda dengan memakan bagian dalam pelepah daun dan batang, menyebabkan garis-garis atau bercak keputihan pada daun dan kadang-kadang kematian tunas. Berbeda dengan 'paddy stem maggot' (Atherigona) yang lebih fokus pada titik tumbuh.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Beberapa musuh alami seperti parasitoid dan predator dapat membantu mengurangi populasi. Praktik budidaya yang baik mendukung keberadaan musuh alami."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Aplikasi insektisida biasanya tidak diperlukan kecuali jika serangan sangat parah pada tanaman yang sangat muda. Jika diperlukan, insektisida sistemik atau kontak dapat digunakan. Fokus pada perlindungan persemaian dan tanaman muda."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Tanam bibit yang sehat dan sudah cukup umur. Pemupukan berimbang agar tanaman tumbuh vigor. Pengaturan air yang baik di persemaian dan sawah dapat mengurangi serangan."
                )
            ),
            importantNotes = "Kerusakan akibat lalat bibit seringkali dapat pulih seiring pertumbuhan tanaman. Identifikasi yang benar penting untuk membedakannya dari hama lain. Hindari penggunaan insektisida yang tidak perlu.",
            externalLinks = mapOf(
                "Rice Whorl Maggot (Hydrellia) - IRRI" to "https://www.riceknowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/whorl-maggot", // Whorl maggot adalah nama umum untuk Hydrellia
                "Hama Tanaman Padi - UGM (Contoh Publikasi Akademis)" to "https://repository.ugm.ac.id/" // Cari publikasi terkait
            )
        ),
        "rice_water_weevil" to PestDetails(
            displayName = "Kumbang Air Padi",
            scientificName = "Lissorhoptrus oryzophilus",
            description = "Kumbang dewasa memakan daun padi, meninggalkan bekas luka memanjang yang khas. Larva hidup di dalam tanah dan memakan akar padi, menyebabkan tanaman kerdil, menguning, dan jumlah anakan berkurang. Lebih merusak di sawah irigasi yang tergenang terus-menerus.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Kultur Teknis",
                    advice = "Pengeringan sawah secara periodik (intermittent irrigation) dapat sangat efektif membunuh larva yang sensitif terhadap kekeringan. Penundaan penggenangan sawah setelah tanam juga dapat membantu."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Jika serangan berat, insektisida butiran yang diaplikasikan ke tanah atau perlakuan benih dengan insektisida sistemik dapat mengendalikan larva. Bahan aktif seperti fipronil atau klorantraniliprol bisa dipertimbangkan, namun perhatikan dampaknya pada lingkungan."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Rotasi tanaman dengan tanaman bukan padi atau tanaman lahan kering. Hindari penanaman padi terus menerus di area yang sama jika serangan kumbang air tinggi. Tanam bibit yang lebih tua dan sehat."
                )
            ),
            importantNotes = "Pengelolaan air adalah kunci pengendalian kumbang air padi. Identifikasi dini gejala kerusakan pada daun oleh imago dan pada akar oleh larva penting. Pengendalian kimia harus terintegrasi dengan praktik kultur teknis.",
            externalLinks = mapOf(
                "Rice Water Weevil - University of California IPM" to "https://ipm.ucanr.edu/agriculture/rice/rice-water-weevil/",
                "Hama Kumbang Air - IRRI (Cari jika ada)" to "http://www.knowledgebank.irri.org/"
            )
        ),
        "thrips" to PestDetails(
            displayName = "Thrips Padi",
            scientificName = "Stenchaetothrips biformis (umum pada padi)",
            description = "Thrips dewasa dan nimfa menggaruk dan menghisap cairan dari permukaan daun muda dan pelepah, menyebabkan daun menggulung ke dalam, ujung daun mengering seperti terbakar, dan kadang-kadang muncul bercak keperakan. Serangan berat terjadi pada kondisi kering dan pada tanaman muda.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Beberapa predator alami seperti tungau predator (Amblyseius spp.) dan kepik (Orius spp.) dapat memangsa thrips. Jaga keanekaragaman hayati di sekitar sawah."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Insektisida digunakan jika serangan berat dan menyebabkan daun menggulung lebih dari 50% pada tanaman muda. Insektisida sistemik atau kontak dengan bahan aktif seperti fipronil, imidakloprid, atau spinetoram bisa efektif. Penyemprotan dilakukan saat thrips aktif."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Pastikan ketersediaan air yang cukup terutama pada fase awal pertumbuhan tanaman. Tanam bibit yang sehat dan vigor. Hindari stres kekeringan pada tanaman."
                )
            ),
            importantNotes = "Tanaman padi biasanya dapat pulih dari serangan thrips ringan jika kondisi lingkungan mendukung (misalnya hujan). Penggunaan insektisida harus bijaksana untuk menghindari resurgensi hama dan dampak negatif pada musuh alami.",
            externalLinks = mapOf(
                "Rice Thrips - IRRI" to "https://www.riceknowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/thrips",
                "Pengendalian Thrips Secara Umum - PennState Extension (Contoh)" to "https://extension.psu.edu/thrips-management"
            )
        ),
        "yellow_rice_borer" to PestDetails(
            displayName = "Penggerek Batang Padi Kuning",
            scientificName = "Scirpophaga incertulas",
            description = "Larva penggerek batang padi kuning melubangi batang padi, menyebabkan gejala 'sundep' (pucuk mati) pada fase vegetatif atau 'beluk' (malai hampa dan putih) pada fase generatif. Merupakan salah satu hama utama padi di Asia.",
            imageUrlExample = null,
            recommendations = listOf(
                Recommendation(
                    category = "Pengendalian Hayati",
                    advice = "Pemanfaatan parasitoid telur seperti Trichogramma japonicum, Telenomus spp., dan Tetrastichus spp. Predator seperti laba-laba dan kepik juga berperan. Penggunaan feromon seks untuk mengganggu perkawinan atau monitoring."
                ),
                Recommendation(
                    category = "Pengendalian Kimiawi",
                    advice = "Aplikasi insektisida (butiran atau semprot) dilakukan berdasarkan ambang batas ekonomi (misalnya, >10% anakan terserang sundep atau >2 kelompok telur/m²). Bahan aktif yang efektif antara lain klorantraniliprol, fipronil, atau flubendiamida. Rotasi bahan aktif penting."
                ),
                Recommendation(
                    category = "Pencegahan",
                    advice = "Tanam varietas tahan. Penanaman serentak dan rotasi tanaman. Pengumpulan kelompok telur secara manual. Sanitasi sisa tanaman setelah panen (membajak atau membakar jerami yang terinfestasi) untuk membunuh larva dan pupa."
                )
            ),
            importantNotes = "Pengendalian terpadu merupakan pendekatan terbaik. Pemantauan rutin populasi hama dan musuh alami sangat penting. Penggunaan insektisida harus sesuai dengan rekomendasi dan memperhatikan dampaknya terhadap lingkungan.",
            externalLinks = mapOf(
                "Yellow Stem Borer - IRRI" to "https://www.riceknowledgebank.irri.org/training/fact-sheets/pest-management/insects/item/yellow-stem-borer",
                "Info Penggerek Kuning - Direktorat Perlindungan Tanaman (Contoh)" to "http://ditjenpp.pertanian.go.id/index.php/direktorat-perlindungan-tanaman-pangan/opt-tanaman-padi"
            )
        )
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