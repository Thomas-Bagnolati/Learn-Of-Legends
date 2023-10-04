package com.bagnolati.learnoflegends.core.model

/**
 * Class summarizing user interest data
 */
data class UserData(
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val shouldHideOnboarding: Boolean
)
