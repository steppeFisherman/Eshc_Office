package com.example.eshccheck.utils

import java.util.*

interface AlarmCommentUpdate {

    fun update(comment: String, result: (MutableMap<String, Any>) -> Unit)

    class Base(private val format: DateTimeFormat) : AlarmCommentUpdate {
        override fun update(comment: String, result: (MutableMap<String, Any>) -> Unit) {
            val dateLong = Calendar.getInstance(Locale.getDefault()).time.time
            val map = mutableMapOf<String, Any>()

            map[CHILD_ALARM] = false
            map[CHILD_LOCATION_FLAG_ONLY] = false
            map[CHILD_COMMENT] = comment
            map[CHILD_TIME] = format.longToStringFormat(dateLong)
            map[CHILD_TIME_LONG] = dateLong
            result(map)
        }
    }
}