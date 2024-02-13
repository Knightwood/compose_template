import com.kiylx.common.logic.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.0"
    alias(buildLibs.plugins.buildLogic.android.empty)

}

android {
    namespace = "com.kiylx.libx"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        //noinspection ExpiredTargetSdkVersion
        targetSdk = 32

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
    kotlinProject(compileOnlyDeps)
    androidTest()
    //Retrofit
    configIo(apiDeps)
    //权限申请
    compileOnly(others.github.perms)
    //工具库
    compileOnly(others.github.utilcodex)
    compileOnly(others.github.mmkv)

}