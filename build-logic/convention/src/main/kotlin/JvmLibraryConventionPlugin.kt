import com.bagnolati.learnoflegends.configureKotlinJvm
import dsl.applyPlugin
import dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                applyPlugin(libs.plugins.kotlin.jvm)
            }
            configureKotlinJvm()
        }
    }
}
