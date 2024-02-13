package com.kiylx.common.dependences

//compose依赖
object Compose {
    val names = ComposeLibNames()
    val libs = ComposeLibs()
}

/**
 * compose库集合
 */
class ComposeLibs internal constructor() {

}

/**
 * 一些命名的集合
 */
class ComposeLibNames internal constructor() {
    val composeBom = ComposeWithBomName
}

/**
 * COMPOSE bom 中包含的依赖库的名
 */
object ComposeWithBomName {
    const val bom = "androidx.compose:compose-bom"

    //动画
    const val animation = "androidx.compose.animation:animation"
    const val animationCore = "androidx.compose.animation:animation-core"

    //compose基础库
    const val animationGraphics = "androidx.compose.animation:animation-graphics"
    const val foundation = "androidx.compose.foundation:foundation"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout"

    //material
    const val material2 = "androidx.compose.material:material"
    const val materialIconsCore = "androidx.compose.material:material-icons-core"
    const val materialIconsExtended = "androidx.compose.material:material-icons-extended"
    const val materialRipple = "androidx.compose.material:material-ripple"
    const val material3 = "androidx.compose.material3:material3"
    const val material3WindowSizeClass = "androidx.compose.material3:material3-window-size-class"

    //RUNTIME
    const val runtime = "androidx.compose.runtime:runtime"
    const val runtimeLivedata = "androidx.compose.runtime: runtime-livedata"
    const val runtimeRxjava2 = "androidx.compose.runtime: runtime-rxjava2"
    const val runtimeRxjava3 = "androidx.compose.runtime: runtime-rxjava3"
    const val runtimeSaveable = "androidx.compose.runtime: runtime-saveable"

    //UI
    const val ui = "androidx.compose.ui: ui"
    const val uiGeometry = "androidx.compose.ui: ui-geometry"
    const val uiGraphics = "androidx.compose.ui: ui-graphics"
    const val uiTest = "androidx.compose.ui: ui-test"
    const val uiTestJunit4 = "androidx.compose.ui: ui-test-junit4"
    const val uiTestManifest = "androidx.compose.ui: ui-test-manifest"
    const val uiText = "androidx.compose.ui: ui-text"
    const val uiTextGoogleFonts = "androidx.compose.ui: ui-text-google-fonts"
    const val uiTooling = "androidx.compose.ui: ui-tooling"
    const val uiToolingData = "androidx.compose.ui: ui-tooling-data"
    const val uiToolingPreview = "androidx.compose.ui: ui-tooling-preview"
    const val uiUnit = "androidx.compose.ui: ui-unit"
    const val uiUtil = "androidx.compose.ui: ui-util"
    const val uiViewBinding = "androidx.compose.ui: ui-viewbinding"
}
