package com.abdulrahman.nytimes.main.ui.bases

import android.view.View

interface OnListItemClickListener<T> {
    fun onItemClick(view: View, model: T)
}