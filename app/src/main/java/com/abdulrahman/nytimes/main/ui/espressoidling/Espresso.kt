package com.abdulrahman.nytimes.main.ui.espressoidling

object Espresso {

    private const val RESOURCE = "VIEW_MODEL"

    private val mCountingIdlingResource = BackGroundIdlingResource(RESOURCE)

    fun increment() = mCountingIdlingResource.increment()

    fun decrement() = mCountingIdlingResource.decrement()

    fun getIdlingResource() = mCountingIdlingResource
}