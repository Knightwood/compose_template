package plugin

import com.kiylx.common.logic.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("配置hilt")
        //配置hilt
        with(target) {
            val that = this
            //配置plugin
            with(pluginManager) {
                apply("org.jetbrains.kotlin.kapt")
                apply("com.google.dagger.hilt.android")
            }
            libraryOrApp {
                // Allow references to generated code
                that.kapt {
                    correctErrorTypes = true
                }

                dependencies {
                    implementationDeps(that.libs2.libFind("google-hilt-android"))
                    kaptDeps(that.libs2.libFind("google-hilt-android-compiler"))
                }

            }

        }
    }
}

