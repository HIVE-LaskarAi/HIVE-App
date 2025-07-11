package com.laskarai.hive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.laskarai.hive.classifier.RicePestClassifier
import com.laskarai.hive.ui.navigation.AppNavigator
import com.laskarai.hive.ui.theme.HIVETheme

class MainActivity : ComponentActivity() {

    private lateinit var classifier: RicePestClassifier

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Inisialisasi classifier saat Activity dibuat
        classifier = RicePestClassifier(applicationContext)

        setContent {
            HIVETheme {
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