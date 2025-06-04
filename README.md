# HIVE (Hama Identification and Visual Evaluation)

Aplikasi Android berbasis Jetpack Compose yang bertujuan untuk membantu petani dan pengguna lainnya dalam mengidentifikasi hama padi melalui gambar yang diambil menggunakan kamera atau dari galeri. Aplikasi ini menggunakan REST API eksternal untuk melakukan analisis gambar dan prediksi jenis hama, kemudian menampilkan informasi detail serta rekomendasi pengelolaan.

## Fitur Utama

* Identifikasi hama padi dari gambar (via galeri atau kamera perangkat).
* Interaksi dengan REST API eksternal untuk klasifikasi gambar berbasis AI.
* Menampilkan nama hama yang teridentifikasi, nama ilmiah (jika tersedia), dan skor kepercayaan prediksi.
* Menyajikan deskripsi detail mengenai karakteristik hama.
* Memberikan rekomendasi pengelolaan hama yang terstruktur per kategori (misalnya, Pengendalian Hayati, Kimiawi, Kultural) lengkap dengan ikon.
* Menampilkan catatan penting terkait hama/pengelolaan.
* Menyediakan tautan ke sumber informasi eksternal yang relevan.
* Antarmuka pengguna yang modern dan intuitif dibangun dengan Jetpack Compose.
* Umpan balik kepada pengguna untuk status operasi dan error menggunakan Snackbar.

## Prasyarat Instalasi

Sebelum memulai, pastikan Anda memiliki:
1.  Android Studio terinstal (versi stabil terbaru direkomendasikan).
2.  Zulu JDK 17 (atau JDK versi lain atau lebih tinggi yang kompatibel).
3.  Emulator Android (API Level 24 atau lebih tinggi) atau perangkat Android fisik.
4.  Koneksi internet yang stabil (untuk sinkronisasi Gradle dan pemanggilan API).

## Pengaturan & Menjalankan Proyek

1.  **Clone Repositori:**
    Buka terminal atau Git Bash, lalu jalankan perintah berikut:
    ```bash
    git clone https://github.com/HIVE-LaskarAi/HIVE-App.git
    cd HIVE-App
    ```
2.  **Buka Proyek di Android Studio:**
    * Buka Android Studio.
    * Pilih "Open" atau "Open an Existing Project".
    * Arahkan ke direktori tempat Anda meng-clone repositori, lalu klik "OK".

3.  **Sinkronisasi Gradle:**
    * Tunggu beberapa saat hingga Android Studio selesai melakukan sinkronisasi Gradle dan mengunduh semua dependensi yang dibutuhkan.
    * Jika ada pesan error terkait versi atau dependensi, periksa file `build.gradle.kts` (level proyek dan modul `app`) dan sesuaikan jika perlu.

## Build & Jalankan Aplikasi

1.  Pastikan emulator Android Anda sudah berjalan atau perangkat Android fisik terhubung ke komputer dengan mode USB Debugging aktif.
2.  Di Android Studio, pilih konfigurasi `app` pada dropdown konfigurasi build (biasanya sudah terpilih secara default).
3.  Pilih target device atau emulator Anda.
4.  Klik tombol "Run" (ikon ▶️ berwarna hijau) atau tekan `Shift + F10`.
