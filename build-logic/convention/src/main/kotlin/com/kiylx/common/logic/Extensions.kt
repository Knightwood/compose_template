package com.kiylx.common.logic

import com.android.build.api.dsl.ApplicationExtension
import com.kiylx.common.dependences.AndroidBuildCode
import com.kiylx.common.dependences.AndroidX
import com.kiylx.common.dependences.Compose
import com.kiylx.common.dependences.IO
import com.kiylx.common.dependences.Kotlin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType


fun DependencyHandlerScope.configComposeModuleDeps(that: Project) {
    val composeBomVersion = AndroidBuildCode.compose_bom

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
    implementation(that.composeLibs2.libFind("androidx-activity-compose"))
    // Optional - Integration with ViewModels
    implementation(that.composeLibs2.libFind("androidx-lifecycle-viewmodel-compose"))
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")

    //test
    androidTestImplementation(platform("androidx.compose:compose-bom:${composeBomVersion}"))

}

fun DependencyHandlerScope.configIo() {
//Retrofit
    api(IO.libs.retrofit2.core)
    api(IO.libs.retrofit2.logging)//日志打印
    api(IO.libs.retrofit2.converterScalars)
    api(Kotlin.libs.serialization.serialization160rc)
    api(IO.libs.retrofit2.converterKotlin)
}

fun DependencyHandlerScope.kotlinProject() {
    implementation(Kotlin.libs.coroutines.core)
    implementation(Kotlin.libs.coroutines.android)
}

fun DependencyHandlerScope.androidTest() {
    "testImplementation"(AndroidX.libs.test.jUnit)
    "androidTestImplementation"(AndroidX.libs.test.androidJUnit)
    "androidTestImplementation"(AndroidX.libs.test.espresso)
}
