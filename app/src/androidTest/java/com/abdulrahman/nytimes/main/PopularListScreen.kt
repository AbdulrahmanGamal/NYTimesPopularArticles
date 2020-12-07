package com.abdulrahman.nytimes.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import com.abdulrahman.nytimes.R
import com.agoda.kakao.Screen


object PopularListScreen : Screen<PopularListScreen>() {

    fun clickOnFirstRecyclerItem() {
        onView(
            RecyclerViewMatcher(R.id.rv_articles).atPositionOnView(0, R.id.container)
        ).perform(click())
    }
}