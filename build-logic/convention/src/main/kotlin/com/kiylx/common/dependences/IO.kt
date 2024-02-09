package com.kiylx.common.dependences

object IO {
    val libs =IoLibs()
}
class IoLibs internal constructor(){
    val sqlite=SQLite
    val okhttp=OkHttp
    val retrofit2 =Retrofit2
}

object SQLite {
    private const val version = "2.3.0"
    const val ktx = "androidx.sqlite:sqlite-ktx:$version"
}


object OkHttp {
    private const val version = "4.11.0"
    const val okhttp = "com.squareup.okhttp3:okhttp:$version"
}
object Retrofit2 {
    const val core = "com.squareup.retrofit2:retrofit:2.9.0"
    const val converterScalars = "com.squareup.retrofit2:converter-scalars:2.6.2"
    const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val logging = "com.squareup.okhttp3:logging-interceptor:4.2.0"

    /**
     * 打印okhttp的log库
     * 使用logging2需要添加 exclude(group = "org.json", module = "json")
     */
    const val logging2 =
        "com.github.ihsanbal:LoggingInterceptor:3.1.0"

    //kotlin的转换器
    const val converterKotlin =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0"

}
