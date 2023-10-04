package com.bagnolati.learnoflegends.core.data.repository

import com.bagnolati.learnoflegends.core.datastore.LolPreferencesDataSource
import com.bagnolati.learnoflegends.core.model.DarkThemeConfig
import com.bagnolati.learnoflegends.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val lolPreferencesDataSource: LolPreferencesDataSource
) : UserDataRepository {

    override val userData: Flow<UserData> =
        lolPreferencesDataSource.userData

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) =
        lolPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) =
        lolPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) =
        lolPreferencesDataSource.setDynamicColorPreference(useDynamicColor)


}
