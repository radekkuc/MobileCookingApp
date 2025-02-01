plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.cookingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cookingapp"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.recyclerview)
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.intents)
    androidTestImplementation(libs.espresso.idling.resource)

}