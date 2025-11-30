plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.qazaqpaybank"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.qazaqpaybank"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    dependencies {
        // Существующие зависимости остаются...

        // Compose
        implementation("androidx.compose.ui:ui:1.5.4")
        implementation("androidx.compose.material3:material3:1.1.2")
        implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
        implementation("androidx.activity:activity-compose:1.8.2")

        // Navigation
        implementation("androidx.navigation:navigation-compose:2.7.6")

        // ViewModel
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

        // Retrofit
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
        implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

        kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

        // QR Code
        implementation("com.google.zxing:core:3.5.2")
        implementation("com.journeyapps:zxing-android-embedded:4.3.0")

        // DataStore
        implementation("androidx.datastore:datastore-preferences:1.0.0")

        // Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

        implementation("androidx.compose.material:material-icons-extended:1.5.4")
        implementation("com.google.ai.client.generativeai:generativeai:0.1.2")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.google.code.gson:gson:2.10.1")

        // Coroutines для async работы
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    }

}

