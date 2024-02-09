import com.kiylx.common.dependences.OtherLibs
import com.kiylx.common.logic.*
import com.kiylx.common.dependences.Compose
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.0"
    alias(buildLibs.plugins.buildLogic.android.app.compose)
}

android {
    namespace = "com.kiylx.composetemplate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kiylx.composetemplate"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/previous-compilation-data.bin"
        }
    }
}

dependencies {
implementation(buildLibs.bundles.bundleAndroidx)
    kotlinProject()
    androidTest()

    //权限申请
    implementation(OtherLibs.libs.perms)
    //lib
    implementation(project(":libx"))
    implementation(project(":compose_lib"))
//    implementation(project(":icon"))

    implementation(Compose.libs.accompanist.systemUiController)
    implementation(composeLibs.androidx.constraintLayout.compose)
    //下拉刷新，上拉加载
//    implementation(ComposeTools.pullRefresh)
//    implementation(Work.manager)
    implementation("com.github.Knightwood:compose-material3-preference:1.0.2")
    // For AppWidgets support
//    implementation(Compose.libs.glance.glance)
    // For interop APIs with Material 3
//    implementation(Compose.libs.glance.glance_material3)
}