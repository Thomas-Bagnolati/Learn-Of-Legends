import dsl.applyPlugin
import dsl.implementation
import dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                applyPlugin(libs.plugins.learnoflegends.android.library)
                applyPlugin(libs.plugins.learnoflegends.android.hilt)
            }

            dependencies {
                implementation(project(":core:model"))
                implementation(project(":core:data"))
                implementation(project(":core:common"))
                implementation(project(":core:domain"))
                implementation(project(":core:ui"))

                implementation(libs.coil.kt)
                implementation(libs.coil.kt.compose)

                implementation(libs.androidx.hilt.navigation.compose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.androidx.lifecycle.viewModelCompose)

                implementation(libs.kotlinx.coroutines.android)
            }
        }
    }
}


