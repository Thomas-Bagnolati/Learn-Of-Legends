package com.bagnolati.learnoflegends.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    data object Loading : Result<Nothing>
}


/**
 * Wrap Flow with [Result]
 */
fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { Result.Success(it) }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it)) }
}

/**
 * Wrap Flow with [Result], retry flow when [retry] emit something.
 *
 * @param retry let's retry flow when it emit something.
 */
fun <T> Flow<T>.asResult(
    retry: Flow<Unit>
): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { Result.Success(it) }
        .onStart { emit(Result.Loading) }
        .retryWhen { cause, _ ->
            emit(Result.Error(cause))
            retry.first()
            true
        }
}