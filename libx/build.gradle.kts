import com.kiylx.common.dependences.*
import com.kiylx.common.logic.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.kiylx.libx"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
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
    implementation(buildLibs.bundles.bundleAndroidx)
    kotlinProject()
    androidTest()

    //Retrofit
    api(IO.libs.retrofit2.core)
    api(IO.libs.retrofit2..logging)//日志打印
    api(IO.libs.retrofit2..converterGson)
    api(IO.libs.retrofit2..converterScalars)
    api(Kotlin.libs.serialization.serialization160rc)
    api(IO.libs.retrofit2..converterKotlin)

  //权限申请
    implementation(OtherLibs.libs.perms)
    //工具库
    api(OtherLibs.libs..utilcodex)
    implementation(OtherLibs.libs.mmkv)

}