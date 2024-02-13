import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "com.kiylx.common.build_logic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)

    //Make version catalog more type safe
//    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
//    implementation(files(composeLibs.javaClass.superclass.protectionDomain.codeSource.location))
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

//tasks.register("registerVersionCatalogs"){
//    doLast{
//
//    }
//}

gradlePlugin {

    plugins {
        register("androidApplication") {
            id = "kiylx.build_logic.android.application"
            implementationClass = "plugin.AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "kiylx.build_logic.android.application.compose"
            implementationClass = "plugin.AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "kiylx.build_logic.android.library.compose"
            implementationClass = "plugin.AndroidLibraryComposeConventionPlugin"
        }
        register("emptyPlugin") {
            id = "kiylx.build_logic.android.library.empty"
            implementationClass = "plugin.AndroidLibraryEmptyConventionPlugin"
        }
        register("roomPlugin") {
            id = "kiylx.build_logic.android.library.room"
            implementationClass = "plugin.AndroidRoomConventionPlugin"
        }
        register("hiltPlugin") {
            id = "kiylx.build_logic.android.library.hilt"
            implementationClass = "plugin.AndroidLibraryHiltConventionPlugin"
        }

    }
}
