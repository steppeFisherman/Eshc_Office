package com.example.eshccheck.utils

import java.text.SimpleDateFormat
import java.util.*

interface DateTimeFormat {

    fun longToStringFormat(time: Long): String

    class Base : DateTimeFormat {
        override fun longToStringFormat(time: Long): String {
            val format = SimpleDateFormat("HH:mm, dd.MMM.yyyy", Locale.getDefault())
            return format.format(time).toString()
        }
    }
}