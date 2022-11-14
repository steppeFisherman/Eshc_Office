package com.example.eshccheck.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eshccheck.R
import com.example.eshccheck.databinding.ActivityMainBinding
import com.example.eshccheck.ui.app.App
import com.example.eshccheck.utils.CHILD_ID
import com.example.eshccheck.utils.NODE_USERS
import com.example.eshccheck.utils.REF_DATABASE_ROOT
import com.example.eshccheck.utils.UsersService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navControllerMain: NavController
    private lateinit var destinationChangedListener:
            NavController.OnDestinationChangedListener
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var preferences: SharedPreferences
    private var firstTimeUser = false


//    private val usersService: UsersService
//        get() = (applicationContext as App).usersService
    private val mapDataCloud = mutableMapOf<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_EshcCheck)
        binding = ActivityMainBinding.inflate(layoutInflater)
        /**
        Changes statusBar text/icons color, when the statusBar background is white or transparent
        (this works with API level 21 and higher)
         */
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = true
        setContentView(binding.root)
        initialise()
//        usersService.addListener(usersListener)
//        createUser()
    }

    private fun createUser() {

//        REF_DATABASE_ROOT.child(NODE_USERS).child(mapDataCloud[CHILD_ID].toString())
//            .updateChildren(mapDataCloud)
//            .addOnCompleteListener { task ->
//            }
    }


    private fun initialise() {
        REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navControllerMain = navHostFragment.navController
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setupWithNavController(navControllerMain)
    }

//        val usersListener: UsersListener = {
//            it.forEach { dataCloud ->
//                mapDataCloud[CHILD_ID] = dataCloud.id
//                mapDataCloud[CHILD_FULL_NAME] = dataCloud.full_name
//                mapDataCloud[CHILD_PHONE_USER] = dataCloud.phone_user
//                mapDataCloud[CHILD_PHONE_OPERATOR] = dataCloud.phone_operator
//                mapDataCloud[CHILD_PHOTO] = dataCloud.photo
//                mapDataCloud[CHILD_TIME] = dataCloud.time_location
//                mapDataCloud[CHILD_LATITUDE] = dataCloud.latitude
//                mapDataCloud[CHILD_LONGITUDE] = dataCloud.longitude
//                mapDataCloud[CHILD_ALARM] = dataCloud.alarm
//                mapDataCloud[CHILD_NOTIFY] = dataCloud.notify

//                REF_DATABASE_ROOT.child(NODE_USERS).child(dataCloud.id.toString())
//                    .updateChildren(mapDataCloud)
//                    .addOnCompleteListener { task ->
//                    }
}

