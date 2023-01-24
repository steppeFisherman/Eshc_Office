package com.example.eshccheck.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext

interface ResourceProvider {

    fun getString(@StringRes id: Int): String

    class Base(@ApplicationContext val context: Context) : ResourceProvider {
        override fun getString(id: Int) = context.getString(id)
    }
}
