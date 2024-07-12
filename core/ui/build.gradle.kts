plugins {
    alias(libs.plugins.learnoflegends.android.library)
    alias(libs.plugins.learnoflegends.android.library.compose)
}

android {
    namespace = "com.bagnolati.learnoflegends.core.ui"
}

dependencies {
    implementation(projects.core.model)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.windowSizeClass)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.coil.kt.compose)

    debugApi(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.core.ktx)
}
