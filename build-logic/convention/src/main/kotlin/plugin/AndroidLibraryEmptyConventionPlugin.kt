package plugin

import com.android.build.api.dsl.LibraryExtension
import com.kiylx.common.logic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType


// library module的通用构建逻辑
class AndroidLibraryEmptyConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("空白配置")
    }
}