package com.laskarai.hive.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.laskarai.hive.classifier.RicePestClassifier
import com.laskarai.hive.ui.screens.classifier.PestClassifierScreen
import com.laskarai.hive.ui.screens.result.PredictionResultScreen

/**
 * Composable utama yang mengatur NavHost dan mendefinisikan grafik navigasi aplikasi.
 *
 * @param navController Controller yang mengelola navigasi antar layar.
 * @param classifier Instance dari RicePestClassifier yang akan digunakan oleh layar yang membutuhkan.
 */
@Composable
fun AppNavigator(navController: NavHostController, classifier: RicePestClassifier) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.CLASSIFIER_SCREEN // Layar awal saat aplikasi dibuka
    ) {
        // Definisi untuk layar utama (klasifikasi hama)
        composable(route = AppRoutes.CLASSIFIER_SCREEN) {
            PestClassifierScreen(
                classifier = classifier,
                navController = navController
            )
        }

        // Definisi untuk layar hasil prediksi
        // Rute ini menyertakan placeholder untuk argumen yang akan diterima
        composable(
            route = "${AppRoutes.RESULT_SCREEN_BASE}/{${AppRoutes.ARG_PREDICTION_LABEL}}/{${AppRoutes.ARG_IMAGE_URI}}/{${AppRoutes.ARG_CONFIDENCE}}",
            arguments = listOf(
                navArgument(AppRoutes.ARG_PREDICTION_LABEL) { type = NavType.StringType },
                navArgument(AppRoutes.ARG_IMAGE_URI) { type = NavType.StringType },
                navArgument(AppRoutes.ARG_CONFIDENCE) { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            // Mengambil argumen dari backStackEntry
            val label = backStackEntry.arguments?.getString(AppRoutes.ARG_PREDICTION_LABEL) ?: "Unknown"
            val imageUriString = backStackEntry.arguments?.getString(AppRoutes.ARG_IMAGE_URI) ?: ""
            val confidence = backStackEntry.arguments?.getFloat(AppRoutes.ARG_CONFIDENCE) ?: 0.0f
            val decodedUriString = Uri.decode(imageUriString)

            PredictionResultScreen(
                navController = navController,
                predictionLabel = label,
                imageUriString = decodedUriString,
                confidence = confidence
            )
        }
    }
}