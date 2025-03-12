plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.apicalls"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.apicalls"
        minSdk = 24
        targetSdk = 35
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/libraries"
            excludes += "META-INF/io.netty.versions.properties"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.appdistribution.gradle)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // ✅ Retrofit for API Calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // ✅ Gson Converter for Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // ✅ Gson Library
    implementation("com.google.code.gson:gson:2.8.9")

    // ✅ OkHttp Logging Interceptor (For API Logging)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
}
