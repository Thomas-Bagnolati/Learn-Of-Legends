package com.bagnolati.learnoflegends.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val lolDispatcher: LolDispatchers)

enum class LolDispatchers {
    Default,
    IO,
}