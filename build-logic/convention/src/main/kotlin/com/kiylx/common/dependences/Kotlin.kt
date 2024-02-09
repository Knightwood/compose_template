package com.kiylx.common.dependences

object Kotlin {
    val libs =KotlinLibs()
}
class KotlinLibs internal constructor(){
    val serialization=KotlinSerialization
    val coroutines=Coroutines
    val ktor =Ktor
}
object KotlinSerialization {
    //kotlin序列化 kt 1.7.10
    const val serialization140 = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0"
    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"

    //kotlin序列化 kt 1.6.21
    const val serialization133 = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3"

    //kotlin序列化 kt 1.8.10
    const val serialization150 = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"

    //kotlin序列化 kt 1.9.0
    const val serialization160rc = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0-RC"
}

object Coroutines {
    private const val coroutinesVersion = "1.7.3"
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
}

object Ktor {
    private const val version = "2.2.3"
    const val core = "io.ktor:ktor-client-core:$version"
    const val okhttp = "io.ktor:ktor-client-okhttp:$version"
}
