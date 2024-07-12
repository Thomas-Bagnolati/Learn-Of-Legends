plugins {
    alias(libs.plugins.learnoflegends.android.feature)
    alias(libs.plugins.learnoflegends.android.library.compose)
}

android {
    namespace = "com.bagnolati.learnoflegends.feature.items"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}
