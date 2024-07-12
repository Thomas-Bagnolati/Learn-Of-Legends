import com.bagnolati.learnoflegends.LolBuildType

plugins {
    alias(libs.plugins.learnoflegends.android.application)
    alias(libs.plugins.learnoflegends.android.application.compose)
    alias(libs.plugins.learnoflegends.android.hilt)
}

android {
    defaultConfig {
        applicationId = "com.bagnolati.learnoflegends"

        versionCode = 6
        versionName = "1.3.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = LolBuildType.DEBUG.applicationIdSuffix
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            applicationIdSuffix = LolBuildType.RELEASE.applicationIdSuffix
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // SignIn is done on GitHub workflow to hide keys.
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    namespace = "com.bagnolati.learnoflegends"
}


dependencies {
    implementation(projects.feature.champions)
    implementation(projects.feature.champion)
    implementation(projects.feature.items)
    implementation(projects.feature.settings)

    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.coil.kt)

    ksp(libs.hilt.compiler)

    debugImplementation(libs.androidx.compose.ui.testManifest)
}

dependencyGuard {
    configuration("prodReleaseRuntimeClasspath")
}
