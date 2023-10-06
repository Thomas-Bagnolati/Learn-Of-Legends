@file:Suppress("UnstableApiUsage")

import com.bagnolati.learnoflegends.ProjectBuildType

plugins {
    alias(libs.plugins.learnoflegends.android.application)
    alias(libs.plugins.learnoflegends.android.applicationCompose)
    alias(libs.plugins.learnoflegends.android.hilt)
}

android {
    defaultConfig {
        applicationId = "com.bagnolati.learnoflegends"
        versionCode = 5
        versionName = "0.1.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        tasks.register("appVersionName") { println(versionName.toString()) }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ProjectBuildType.DEBUG.applicationIdSuffix
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            applicationIdSuffix = ProjectBuildType.RELEASE.applicationIdSuffix
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
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
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.coil.kt)

}