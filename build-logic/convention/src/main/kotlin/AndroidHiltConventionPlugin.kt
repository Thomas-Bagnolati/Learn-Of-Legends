@file:Suppress("UnstableApiUsage")

import dsl.applyPlugin
import dsl.implementation
import dsl.ksp
import dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                applyPlugin(libs.plugins.hilt)
                applyPlugin(libs.plugins.ksp)
            }

            dependencies {
                implementation(libs.hilt.android)
                ksp(libs.hilt.compiler)
            }
        }
    }
}