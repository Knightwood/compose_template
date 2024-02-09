package com.kiylx.common.logic


import com.kiylx.common.dependences.AndroidBuildCode
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

//快速访问libs和composeLibs的扩展属性
//因为plugin会在某个module中使用，那么，这里通过gradle的project获取VersionCatalog时，
//名称得是那个gradle project中已注册catalogs文件时的名称，
//即比如在app module的 build.gradle.kt中应用插件，那么，这里就只能是在项目的setting.gradle.kt文件中创建catalogs文件的那个名称，
//而不是build-logic module的 setting.gradle.kt 中创建的那个名称。
//如果插件是在build-logic的build.gradle.kt中应用插件，就得是在build-logic的setting.gradle.kt文件中创建catalogs文件的那个名称
//所以，直接让build-logic模块setting.gradle.kt文件中注册的名称和项目的setting.gradle.kt文件中注册的名称一致，即可不用关心名称错乱问题
val Project.libs2
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>()
        .named("buildLibs")

val Project.composeLibs2
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>()
        .named("composeLibs")

//object ProjectExtensions {
//   lateinit var libs:VersionCatalog
//       internal set
//   lateinit var composeLibs:VersionCatalog
//       internal set
//
//}
/**
 * name不是依赖库的名字，而是catalogs文件中定义的名字，例如catalogs文件中有如下依赖
 * buildLogic-android-app-compose = { id = "kiylx.build_logic.android.application.compose", version = "unspecified" }
 * 那么，此方法传入的name就得是"buildLogic-android-app-compose"
 */
fun VersionCatalog.libFind(libName: String) = findLibrary(libName).get()

//引入依赖
fun DependencyHandlerScope.implementation(str: Any) = "implementation"(str)

fun DependencyHandlerScope.api(str: Any) = "api"(str)

fun DependencyHandlerScope.debugImplementation(str: Any) = "debugImplementation"(str)

fun DependencyHandlerScope.androidTestImplementation(str: Any) = "androidTestImplementation"(str)

//带版本号的
fun DependencyHandlerScope.implementation(str: String, ver: String) = "implementation"("$str:$ver")

fun DependencyHandlerScope.api(str: String, ver: String) = "api"(str)

fun DependencyHandlerScope.debugImplementation(str: String, ver: String) =
    "debugImplementation"("$str:$ver")

fun DependencyHandlerScope.androidTestImplementation(str: String, ver: String) =
    "androidTestImplementation"("$str:$ver")

