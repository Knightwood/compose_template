package com.kiylx.common.logic

import com.android.build.api.dsl.ApplicationExtension
import com.kiylx.common.dependences.AndroidX
import com.kiylx.common.dependences.Compose
import com.kiylx.common.dependences.Kotlin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun DependencyHandlerScope.kotlinProject() {
    implementation(Kotlin.libs.coroutines.core)
    implementation(Kotlin.libs.coroutines.android)
}

fun DependencyHandlerScope.androidTest() {
    "testImplementation"(AndroidX.libs.test.jUnit)
    "androidTestImplementation"(AndroidX.libs.test.androidJUnit)
    "androidTestImplementation"(AndroidX.libs.test.espresso)
}
