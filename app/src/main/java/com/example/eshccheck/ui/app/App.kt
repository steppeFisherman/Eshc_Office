package com.example.eshccheck.ui.app

import android.app.Application
import com.example.eshccheck.utils.UsersService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

//    val usersService = UsersService()

    override fun onCreate() {
        super.onCreate()
//        Firebase.database.setPersistenceEnabled(true)
    }
}
