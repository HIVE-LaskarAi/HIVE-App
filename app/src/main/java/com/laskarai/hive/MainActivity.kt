package com.laskarai.hive // Paket root aplikasi Anda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.laskarai.hive.classifier.RicePestClassifier
import com.laskarai.hive.ui.navigation.AppNavigator

class MainActivity : ComponentActivity() {

    private lateinit var classifier: RicePestClassifier
    // Nama file model TFLite Anda yang ada di folder assets
    private val modelFileName = "converted_model.tflite" // Sesuaikan jika nama model berbeda

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi classifier saat Activity dibuat
        classifier = RicePestClassifier(applicationContext, modelFileName)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Membuat NavController untuk mengelola navigasi antar layar
                    val navController = rememberNavController()

                    // Memanggil AppNavigator yang berisi NavHost dan semua definisi layar
                    AppNavigator(navController = navController, classifier = classifier)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // melepaskan resource classifier saat Activity dihancurkan
        // untuk menghindari kebocoran memori.
        if (::classifier.isInitialized) {
            classifier.close()
        }
    }
}