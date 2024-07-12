plugins {
    alias(libs.plugins.learnoflegends.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.bagnolati.learnoflegends.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.javax.inject)
}
