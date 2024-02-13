package plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * 如果某module无法引用build-logic module中的kotlin类，
 * 需在无法引入build-logic module中的kotlin类的module中引入此插件
 */
class AndroidEmptyConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("空白配置")
//        target.libraryOrApp{}
    }
}