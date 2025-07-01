plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.prm392.manga.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.prm392.manga.app"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

// AndroidX
    implementation(libs.viewpager2)
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation(libs.preference)

    // Retrofit + Gson
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.rxjava2)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // RxJava 2
    implementation(libs.rxjava2)
    implementation(libs.rxandroid2)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    // Gson
    implementation(libs.gson)

    implementation (libs.coordinatorlayout)
    implementation (libs.flexbox)

    implementation (libs.photoview)
//    implementation (libs.subsampling.image.view)




}