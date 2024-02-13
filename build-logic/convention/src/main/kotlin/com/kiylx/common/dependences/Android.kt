package com.kiylx.common.dependences

object AndroidX {
    val libs = AndroidXLibs()
}

class AndroidXLibs internal constructor() {
    val test = ViewTest
}



object ViewTest {
    const val jUnit = "junit:junit:4.13.2"
    const val androidJUnit = "androidx.test.ext:junit:1.1.5"
    const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
}
