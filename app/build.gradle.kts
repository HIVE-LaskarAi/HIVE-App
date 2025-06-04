plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.laskarai.hive"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.laskarai.hive"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlinx") {
            when (requested.name) {
                "kotlinx-serialization-core",
                "kotlinx-serialization-core-jvm",
                "kotlinx-serialization-json",
                "kotlinx-serialization-json-jvm" -> {
                    // Paksa ke versi yang kompatibel dengan Kotlin 1.9.0
                    // Anda bisa menggunakan "1.6.0" atau "1.6.3"
                    // "1.6.3" adalah rilis patch terakhir di seri 1.6.x, jadi mungkin lebih baik
                    useVersion("1.6.0")
                    because("Memastikan kompatibilitas dengan Kotlin 1.9.0 dan menghindari konflik versi.")
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.litert.support.api)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.ui)
    implementation(libs.litert)

    // Ktor Client (Android)
    implementation(libs.ktor.client.android)
    // Ktor Client Content Negotiation (untuk JSON)
    implementation(libs.ktor.client.content.negotiation)
    // Ktor Serialization dengan Kotlinx JSON
    implementation(libs.ktor.serialization.kotlinx.json)

    // Kotlinx Serialization JSON (runtime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.splashscreen)

}