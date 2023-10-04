package com.bagnolati.learnoflegends


@Suppress("unused")
enum class ProjectBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}
