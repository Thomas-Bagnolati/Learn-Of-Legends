package com.bagnolati.learnoflegends

enum class LolBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}