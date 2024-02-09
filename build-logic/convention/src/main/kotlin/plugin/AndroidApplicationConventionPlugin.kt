package plugin

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.kiylx.common.dependences.AndroidBuildCode
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions


//通用的构建逻辑
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("BasicBuildLogic")

        with(target) {
            //配置plugin
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            //或者可以这么写配置plugin
//            plugins.run {
//                apply("com.android.application")
//                apply("org.jetbrains.kotlin.android")
//            }
//            extensions.configure<CommonExtension<*, *, *, *, *>>("android") {
//
//            }
//            extensions.configure<ApplicationExtension> {
//
//            }
//            extensions.configure<ApplicationAndroidComponentsExtension>{
//
//            }
            //配置android
            extensions.configure<BaseAppModuleExtension> {

//                namespace = AndroidBuildCode.namespace
                compileSdk = AndroidBuildCode.compileSdk
                defaultConfig {
//                    applicationId = AndroidBuildCode.applicationId
                    minSdk = AndroidBuildCode.minSdk
                    targetSdk = AndroidBuildCode.targetSdk
                    versionCode = AndroidBuildCode.versionCode
                    versionName = AndroidBuildCode.versionName
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//                    vectorDrawables {
//                        useSupportLibrary = true
//                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                viewBinding {
                    enable = true
                }
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_17.toString()
                }
            }
        }
    }
}

fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}