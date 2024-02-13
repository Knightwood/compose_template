package plugin

import com.android.build.api.dsl.ApplicationExtension
import com.kiylx.common.dependences.AndroidBuildCode
import com.kiylx.common.logic.configComposeModuleDeps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("配置compose")
        with(target) {
            /**
             * 注意这里的that，
             * extensions.getByType<VersionCatalogsExtension>()需要在project做接收者的情况下才能查找到catalogs文件
             * ,所以在这里定义了一下that去指向方法定义时的接收者，即project对象。
             * 而且，这里查找的catalogs文件，是依靠project的，而这里的project,是app module的build.gradle.kt
             * 因此，查找的catalogs文件也是在 app module 所处的环境定义/引入的catalogs文件，
             * 即，项目的setting.gradle.kt中创建的catalogs文件
             */
            val that = this

            libraryOrApp {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = AndroidBuildCode.compose_compiler_version
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
                dependencies {
                    configComposeModuleDeps(that)
                }

//                testOptions {
//                    unitTests {
//                         // For Robolectric
//                        isIncludeAndroidResources = true
//                    }
//                }

            }

//            that.tasks.withType<KotlinCompile>().configureEach {
//                kotlinOptions {
//                    freeCompilerArgs = freeCompilerArgs + buildComposeMetricsParameters()
//                }
//            }
        }
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters = mutableListOf<String>()
    val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
    val relativePath = projectDir.relativeTo(rootDir)
    val buildDir = layout.buildDirectory.get().asFile
    val enableMetrics = (enableMetricsProvider.orNull == "true")
    if (enableMetrics) {
        val metricsFolder = buildDir.resolve("compose-metrics").resolve(relativePath)
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + metricsFolder.absolutePath
        )
    }

    val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
    val enableReports = (enableReportsProvider.orNull == "true")
    if (enableReports) {
        val reportsFolder = buildDir.resolve("compose-reports").resolve(relativePath)
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + reportsFolder.absolutePath
        )
    }
    return metricParameters.toList()
}