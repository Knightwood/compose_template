import com.kiylx.common.logic.*
import com.kiylx.common.dependences.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(buildLibs.plugins.buildLogic.android.compose)
}

android {
    namespace = "com.kiylx.compose_lib"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {
    compileOnly(buildLibs.bundles.bundleAndroidx)
    compileOnly(buildLibs.google.material){
        exclude("androidx.activity","activity")
        exclude("androidx.appcompat","appcompat")
        exclude("androidx.constraintlayout","constraintlayout")
        exclude("androidx.core","core")
        exclude("androidx.recyclerview","recyclerview")
    }
    implementation(buildLibs.bundles.kotlins)
    compileOnly(buildLibs.bundles.retrofit2)
    androidTest()

    //datastore
    implementation(buildLibs.bundles.dataStore) {
        exclude("org.jetbrains.kotlinx","kotlinx-coroutines-core")
    }
    implementation(others.github.svgSupport)
    compileOnly(others.github.mmkv)
    compileOnly(others.coil.kt.compose)
    compileOnly(composeLibs.androidx.navigation.compose)
    compileOnly(composeLibs.google.accompanist.systemUiController)
    compileOnly(composeLibs.androidx.constraintLayout.compose)

    implementation(others.github.m3Color)
}
