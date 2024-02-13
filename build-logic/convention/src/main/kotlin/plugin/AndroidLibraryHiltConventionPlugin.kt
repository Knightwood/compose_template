package plugin

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.kiylx.common.logic.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies


//compose library module的通用构建逻辑
class AndroidLibraryHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("配置hilt")
        //配置compose
        with(target) {
            val that = this
            //配置plugin
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
                apply("com.google.dagger.hilt.android")
            }
            extensions.configure<BaseAppModuleExtension> {

                // Allow references to generated code
                that.kapt {
                    correctErrorTypes = true
                }

                dependencies {
                    implementation(that.libs2.libFind("hilt-android"))
                    "kapt"(that.libs2.libFind("hilt-android-compiler"))
                }

            }

        }
    }
}

