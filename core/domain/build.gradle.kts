import dsl.ksp

plugins {
    alias(libs.plugins.learnoflegends.android.library)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "com.bagnolati.learnoflegends.core.domain"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.network)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
