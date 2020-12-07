package com.abdulrahman.nytimes.main.ui.bases

import androidx.lifecycle.ViewModel
import com.abdulrahman.nytimes.main.ui.bases.result.Single
import com.abdulrahman.nytimes.main.ui.espressoidling.Espresso
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
    private var mainContext: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), CoroutineScope {


    /**region Coroutines*/
    private val job: Job = Job()
    override val coroutineContext: CoroutineContext get() = mainContext + job

    /**endregion*/

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    open fun launchJob(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        Espresso.increment()
        val job = launch(coroutineContext, start) {
            block()

        }
        job.invokeOnCompletion {
            Espresso.decrement()
        }
        return job
    }

    var requestInProgress = false
        private set



    fun <T : Any> performApiCall(liveData: StateLiveData<T>, result: Single<T>) {
        when (result) {
            is Single.Success -> {
                publishResult(liveData, result.data)
            }
            is Single.Error -> {
                publishError(liveData, result.exception)
            }
        }
    }

    fun <T> publishLoading(liveData: StateLiveData<T>) {
        requestInProgress = true
        liveData.postLoading()
    }

    fun <T> publishNoInternet(liveData: StateLiveData<T>) {
        requestInProgress = false
        liveData.postNoInternet()
    }

    fun <T> publishNotCompleted(liveData: StateLiveData<T>) {
        requestInProgress = false
        liveData.postNotComplete()
    }

    fun <T> publishError(liveData: StateLiveData<T>, t: Throwable) {
        requestInProgress = false
        liveData.postError(t)
    }

    fun <T> publishResult(liveData: StateLiveData<T>, data: T) {
        requestInProgress = false
        liveData.postSuccess(data)
    }
}