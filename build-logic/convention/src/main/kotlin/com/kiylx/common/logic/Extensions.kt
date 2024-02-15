package com.kiylx.common.logic

import com.kiylx.common.dependences.AndroidBuildCode
import com.kiylx.common.dependences.AndroidX
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope


fun DependencyHandlerScope.configComposeModuleDeps(that: Project) {
    val composeBomVersion = AndroidBuildCode.compose_bom

    val composeBom = platform("androidx.compose:compose-bom:${composeBomVersion}")
    implementationDeps(composeBom)
    androidTestImplementationDeps(composeBom)

    // Choose one of the following:
    // Material Design 3
    implementationDeps("androidx.compose.material3:material3")
    // or Material Design 2
//          implementation("androidx.compose.material:material")
    // or skip Material Design and build directly on top of foundational components
//          implementation("androidx.compose.foundation:foundation")
    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
//          implementation("androidx.compose.ui:ui")

    // Android Studio Preview support
    implementationDeps("androidx.compose.ui:ui-tooling-preview")
    debugImplementationDeps("androidx.compose.ui:ui-tooling")

    // UI Tests
    androidTestImplementationDeps("androidx.compose.ui:ui-test-junit4")
    debugImplementationDeps("androidx.compose.ui:ui-test-manifest")

    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
//    implementationDeps("androidx.compose.material:material-icons-core")
    implementationDeps("androidx.compose.material:material-icons-extended")
    // Optional - Add full set of material icons
//          implementation("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
    implementationDeps("androidx.compose.material3:material3-window-size-class")
    // Optional - Integration with activities
    implementationDeps(that.composeLibs2.libFind("androidx-activity-compose"))
    // Optional - Integration with ViewModels
    implementationDeps(that.composeLibs2.libFind("androidx-lifecycle-viewmodel-compose"))
    // Optional - Integration with LiveData
    implementationDeps("androidx.compose.runtime:runtime-livedata")

    //test
    androidTestImplementationDeps(platform("androidx.compose:compose-bom:${composeBomVersion}"))

}


fun DependencyHandlerScope.androidTest() {
    testImplementationDeps(AndroidX.libs.test.jUnit)
    androidTestImplementationDeps(AndroidX.libs.test.androidJUnit)
    androidTestImplementationDeps(AndroidX.libs.test.espresso)
}
