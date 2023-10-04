import com.android.build.api.dsl.ApplicationExtension
import com.bagnolati.learnoflegends.configureKotlinAndroid
import dsl.applyPlugin
import dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                applyPlugin(libs.plugins.android.application)
                applyPlugin(libs.plugins.kotlin.android)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
            }

        }
    }

}
