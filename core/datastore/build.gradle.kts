plugins {
    alias(libs.plugins.learnoflegends.android.library)
    alias(libs.plugins.learnoflegends.android.hilt)
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = "com.bagnolati.learnoflegends.core.datastore"
}

dependencies {
    api(libs.androidx.dataStore.core)
    api(projects.core.datastoreProto)
    api(projects.core.model)

    implementation(projects.core.common)
}
