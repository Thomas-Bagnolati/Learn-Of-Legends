plugins {
    alias(libs.plugins.learnoflegends.android.feature)
    alias(libs.plugins.learnoflegends.android.libraryCompose)
}

android {
    namespace = "com.bagnolati.learnoflegends.feature.items"
}

dependencies {

    implementation(libs.material)
}
