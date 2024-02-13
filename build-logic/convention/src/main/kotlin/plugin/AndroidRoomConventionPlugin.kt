package plugin

import com.android.build.api.dsl.LibraryExtension
import com.kiylx.common.logic.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("配置room")
        with(target) {
            val that = this
            with(pluginManager) {
                apply("org.jetbrains.kotlin.kapt")
            }
            libraryOrApp {
                dependencies {

                    implementation(that.libs2.libFind("androidx-room-runtime"))
                    annotationProcessor(that.libs2.libFind("androidx-room-compiler"))
                    // To use Kotlin annotation processing tool (kapt)
                    kaptAnnotationProcessor(that.libs2.libFind("androidx-room-compiler"))
                    // optional - Kotlin Extensions and Coroutines support for Room
                    implementation(that.libs2.libFind("androidx-room-ktx"))
                    // optional - Test helpers
                    testImplementation(that.libs2.libFind("androidx-room-testing"))
                    // optional - Paging 3 Integration
                    implementation(that.libs2.libFind("androidx-room-paging"))

//                    val room_version = "2.6.1"
                    // To use Kotlin Symbol Processing (KSP)
//                    ksp("androidx.room:room-compiler:$room_version")
                    // optional - RxJava2 support for Room
//                    implementation("androidx.room:room-rxjava2:$room_version")
                    // optional - RxJava3 support for Room
//                    implementation("androidx.room:room-rxjava3:$room_version")
                    // optional - Guava support for Room, including Optional and ListenableFuture
//                    implementation("androidx.room:room-guava:$room_version")

                }
            }
        }
    }
}

