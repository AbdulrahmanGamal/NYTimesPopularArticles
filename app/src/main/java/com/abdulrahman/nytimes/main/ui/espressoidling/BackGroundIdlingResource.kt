package com.abdulrahman.nytimes.main.ui.espressoidling

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

class BackGroundIdlingResource(private val mResourceName: String) : IdlingResource {

    private val counter = AtomicInteger(0)

    @Volatile
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName() = mResourceName

    override fun isIdleNow() = counter.get() == 0

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }


    fun increment() = counter.getAndIncrement()

    fun decrement() {
        val counterVal = counter.decrementAndGet()
        if (counterVal == 0) {
            resourceCallback?.onTransitionToIdle()
        } else if (counterVal < 0) {
            throw IllegalArgumentException("Counter has been corrupted!")
        }
    }
}