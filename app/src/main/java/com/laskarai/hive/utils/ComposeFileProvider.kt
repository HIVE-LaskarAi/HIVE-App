package com.laskarai.hive.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Object helper untuk membuat URI gambar menggunakan FileProvider.
 * Ini diperlukan untuk menyediakan lokasi penyimpanan sementara bagi gambar yang diambil dengan kamera,
 * terutama pada Android versi lebih baru yang membatasi akses URI file langsung.
 */
object ComposeFileProvider {

    /**
     * Menghasilkan URI konten yang aman untuk file gambar baru.
     * File gambar akan dibuat di direktori cache internal aplikasi.
     *
     * @param context Konteks aplikasi, digunakan untuk mengakses direktori cache dan package name.
     * @return Uri dari file gambar yang baru dibuat, atau null jika terjadi kesalahan.
     */
    fun getImageUri(context: Context): Uri {
        // Membuat nama file unik berdasarkan timestamp
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"

        // Menentukan direktori penyimpanan di dalam cache aplikasi
        // Menggunakan context.cacheDir lebih aman dan tidak memerlukan izin penyimpanan eksternal.
        val storageDir = File(context.cacheDir, "images")
        storageDir.mkdirs() // Membuat direktori jika belum ada

        // Membuat file gambar sementara
        val imageFile = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        )

        // Mendapatkan authority untuk FileProvider.
        // Authority ini harus sama dengan yang dideklarasikan di AndroidManifest.xml.
        // Menggunakan context.packageName memastikan authority unik untuk aplikasi
        val authority = "${context.packageName}.fileprovider"

        return FileProvider.getUriForFile(
            context,
            authority,
            imageFile
        )
    }
}