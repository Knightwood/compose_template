package com.kiylx.common.dependences

object AndroidX {
    val libs = AndroidXLibs()
}

class AndroidXLibs internal constructor() {
    val navigation = Navigation
    val hilt = Hilt
    val dataStore = Datastore
    val work = Work
    val room = Room
    val test = ViewTest
}

object Navigation {
    private const val nav_version = "2.7.4"
    const val runtime_ktx = "androidx.navigation:navigation-runtime-ktx:$nav_version"
    const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:$nav_version"
    const val ui_ktx = "androidx.navigation:navigation-ui-ktx:$nav_version"
    const val dynamicFeature =
        "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"
}

object Datastore {
    private const val version = "1.0.0"

    // Preferences DataStore（可以直接使用）
    const val datastorePrefs = "androidx.datastore:datastore-preferences:$version"

    // Preferences DataStore （没有Android依赖项，包含仅适用于 Kotlin 的核心 API）
    const val datastorePrefsCore = "androidx.datastore:datastore-preferences-core:$version"

    // Typed DataStore （Proto DataStore）
    const val datastore = "androidx.datastore:datastore:$version"

    // Typed DataStore （没有Android依赖项，包含仅适用于 Kotlin 的核心 API）
    const val datastoreCore = "androidx.datastore:datastore-core:$version"

}

object Hilt {
    private const val version = "2.44.2"
    const val classpath = "com.google.dagger:hilt-android-gradle-plugin:$version"
    const val android = "com.google.dagger:hilt-android:$version"

    const val compiler = "com.google.dagger:hilt-compiler:$version"
    const val plugin = "dagger.hilt.android.plugin"
    private const val androidXHilt = "1.0.0"
    const val work = "androidx.hilt:hilt-work:$androidXHilt"
    const val androidX = "androidx.hilt:hilt-compiler:$androidXHilt"
}

object ViewTest {
    const val jUnit = "junit:junit:4.13.2"
    const val androidJUnit = "androidx.test.ext:junit:1.1.5"
    const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
}

object Work {
    private const val version = "2.8.1"
    const val manager = "androidx.work:work-runtime-ktx:$version"
}

object Room {
    private const val roomVersion = "2.5.0"
    const val runtime = "androidx.room:room-runtime:$roomVersion"
    const val compiler = "androidx.room:room-compiler:$roomVersion"
    const val ktx = "androidx.room:room-ktx:$roomVersion"
}
