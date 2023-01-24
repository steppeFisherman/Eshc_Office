package com.example.eshccheck.data.repository

import android.util.Log
import com.example.eshccheck.domain.model.ErrorType
import com.example.eshccheck.domain.model.ResultUser
import com.google.firebase.FirebaseException
import java.net.UnknownHostException

interface ExceptionHandle {

    fun handle(exception: Exception?): ResultUser

    class Base : ExceptionHandle {
        override fun handle(exception: Exception?): ResultUser =
            ResultUser.Fail(
                when (exception) {
                    is UnknownHostException -> ErrorType.NO_CONNECTION
                    is FirebaseException -> ErrorType.FIREBASE_EXCEPTION
                    else -> {
                        Log.d("BBB", "exception: ${exception?.message.toString()}")
                        ErrorType.GENERIC_ERROR
                    }
                }
            )
    }
}
