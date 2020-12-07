package com.abdulrahman.nytimes.main.ui.bases.result

sealed class Single<out T : Any> {
    class Success<out T : Any>(val data: T) : Single<T>()
    class Error(val exception: Throwable) : Single<Nothing>()
}
