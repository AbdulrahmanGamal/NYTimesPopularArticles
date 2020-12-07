package com.abdulrahman.nytimes.main

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.abdulrahman.nytimes.MainActivity
import com.abdulrahman.nytimes.main.ui.espressoidling.Espresso
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class MainScreenTest {


    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun setup() = runBlocking {
        IdlingRegistry.getInstance().register(Espresso.getIdlingResource())
        return@runBlocking
    }

    @Test
    fun addItemToFav() {
        PopularListScreen.clickOnFirstRecyclerItem()
        Thread.sleep(2000)
    }

    @After
    fun onTestFinish() {
        IdlingRegistry.getInstance().unregister(Espresso.getIdlingResource())
    }
}
