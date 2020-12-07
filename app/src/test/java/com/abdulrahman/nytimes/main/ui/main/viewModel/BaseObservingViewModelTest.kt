package com.abdulrahman.nytimes.main.ui.main.viewModel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get

open class BaseObservingViewModelTest : KoinTest {
    lateinit var context: Context

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    open fun startTest() {
        context = get()
    }

    protected fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }


    private class OneTimeObserver<T>(private val handler: (T) -> Unit) : Observer<T>,
        LifecycleOwner {
        private val lifecycle = LifecycleRegistry(this)

        init {
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        override fun getLifecycle(): Lifecycle = lifecycle

        override fun onChanged(t: T) {
            handler(t)
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }
    }

    @After
    fun endTest() {
        stopKoin()
    }
}