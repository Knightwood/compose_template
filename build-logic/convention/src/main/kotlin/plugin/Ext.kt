package plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

fun Project.kapt(block: KaptExtension.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kapt", block)
}

fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

/**
 * 判断是library module还是app module
 */
fun Project.libraryOrApp(block: CommonExtension<*, *, *, *, *>.() -> Unit) {
    val isLibrary: Boolean = pluginManager.hasPlugin("com.android.library")
    println("isLibrary:$isLibrary")
    val extension = if (isLibrary) {
        extensions.getByType<LibraryExtension>()
    } else {
        extensions.getByType<ApplicationExtension>()
    }
    extension.block()
}
