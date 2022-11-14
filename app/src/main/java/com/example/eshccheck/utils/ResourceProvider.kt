package com.example.eshccheck.utils

import android.content.Context
import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(context: Context, @StringRes id: Int): String

    class Base : ResourceProvider {
        override fun getString(context: Context, id: Int) = context.getString(id)
    }
}