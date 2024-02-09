package plugin

import com.android.build.api.dsl.ApplicationExtension
import com.kiylx.common.logic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType


//compose application module的通用构建逻辑
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("配置compose")
        with(target) {
            //配置compose
            pluginManager.apply("com.android.application")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }
}