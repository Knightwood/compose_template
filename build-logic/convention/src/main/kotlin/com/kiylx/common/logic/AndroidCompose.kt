package com.kiylx.common.logic

import com.android.build.api.dsl.CommonExtension
import com.kiylx.common.dependences.AndroidBuildCode
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    /**
     * 注意这里的that，
     * extensions.getByType<VersionCatalogsExtension>()需要在project做接收者的情况下才能查找到catalogs文件
     * ,所以在这里定义了一下that去指向方法定义时的接收者，即project对象。
     * 而且，这里查找的catalogs文件，是依靠project的，而这里的project,是app module的build.gradle.kt
     * 因此，查找的catalogs文件也是在 app module 所处的环境定义/引入的catalogs文件，
     * 即，项目的setting.gradle.kt中创建的catalogs文件
     */
    val that = this
    val composeBomVersion = AndroidBuildCode.compose_bom

    commonExtension.apply {
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
            val composeBom = platform("androidx.compose:compose-bom:${composeBomVersion}")
            implementation(composeBom)
            androidTestImplementation(composeBom)

            // Choose one of the following:
            // Material Design 3
            implementation("androidx.compose.material3:material3")
            // or Material Design 2
//          implementation("androidx.compose.material:material")
            // or skip Material Design and build directly on top of foundational components
//          implementation("androidx.compose.foundation:foundation")
            // or only import the main APIs for the underlying toolkit systems,
            // such as input and measurement/layout
//          implementation("androidx.compose.ui:ui")

            // Android Studio Preview support
            implementation("androidx.compose.ui:ui-tooling-preview")
            debugImplementation("androidx.compose.ui:ui-tooling")

            // UI Tests
            androidTestImplementation("androidx.compose.ui:ui-test-junit4")
            debugImplementation("androidx.compose.ui:ui-test-manifest")

            // Optional - Included automatically by material, only add when you need
            // the icons but not the material library (e.g. when using Material3 or a
            // custom design system based on Foundation)
            implementation("androidx.compose.material:material-icons-core")
            // Optional - Add full set of material icons
//          implementation("androidx.compose.material:material-icons-extended")
            // Optional - Add window size utils
            implementation("androidx.compose.material3:material3-window-size-class")
            // Optional - Integration with activities
            implementation(that.composeLibs.libFind("androidx-activity-compose"))
            // Optional - Integration with ViewModels
            implementation(that.composeLibs.libFind("androidx-lifecycle-viewmodel-compose"))
            // Optional - Integration with LiveData
            implementation("androidx.compose.runtime:runtime-livedata")

            //test
            androidTestImplementation(platform("androidx.compose:compose-bom:${composeBomVersion}"))

        }

//        testOptions {
//            unitTests {
//                // For Robolectric
//                isIncludeAndroidResources = true
//            }
//        }
    }

//    tasks.withType<KotlinCompile>().configureEach {
//        kotlinOptions {
//            freeCompilerArgs = freeCompilerArgs + buildComposeMetricsParameters()
//        }
//    }
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