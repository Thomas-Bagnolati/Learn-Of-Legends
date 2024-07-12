plugins {
    alias(libs.plugins.learnoflegends.android.library)
    alias(libs.plugins.learnoflegends.android.hilt)
}

android {
    namespace = "com.bagnolati.learnoflegends.core.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.datastore)
    api(projects.core.network)

    implementation(libs.retrofit.core)
    implementation(libs.kotlinx.serialization.json)

}