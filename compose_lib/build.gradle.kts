import com.kiylx.common.logic.*
import com.kiylx.common.dependences.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(buildLibs.plugins.buildLogic.android.library.compose)
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
    implementation(buildLibs.bundles.bundleAndroidx)
    kotlinProject()
    androidTest()

    //datastore
    implementation(AndroidX.libs.dataStore.datastorePrefs)
    implementation(AndroidX.libs.dataStore.datastore)

    implementation(OtherLibs.libs.mmkv)
    api(composeLibs.coil.compose)

//    implementation(Tools.svgSupport)
//    api(OtherLibs.libs.m3Color)
}
