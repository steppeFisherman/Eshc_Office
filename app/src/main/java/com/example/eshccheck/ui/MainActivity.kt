package com.example.eshccheck.ui

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eshccheck.R
import com.example.eshccheck.data.model.cloudModel.DataCloud
import com.example.eshccheck.databinding.ActivityMainBinding
import com.example.eshccheck.ui.screens.MainFragment
import com.example.eshccheck.utils.NODE_USERS
import com.example.eshccheck.utils.REF_DATABASE_ROOT
import com.example.eshccheck.utils.SnapShotChildListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainFragment.PermissionHandle {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navControllerMain: NavController
    private lateinit var destinationChangedListener:
            NavController.OnDestinationChangedListener
    private lateinit var bottomNavigationView: BottomNavigationView

//    private lateinit var player: MediaPlayer
    private lateinit var preferences: SharedPreferences
    private var firstTimeUser = false
    private val viewModel by viewModels<MainActivityViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestLocationPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),   // contract for requesting more than 1 permission
        ::onGotLocationPermissionsResult
    )


//
//    private val usersService: UsersService
//        get() = (applicationContext as App).usersService
//    private val mapDataCloud = mutableMapOf<String, Any>()

    @RequiresApi(Build.VERSION_CODES.M)
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

        requestLocationPermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        initialise()
//        observe()
//        usersService.addListener(usersListener)
//        createUser()
    }

//    private fun createUser() {
//        REF_DATABASE_ROOT.child(NODE_USERS).child(mapDataCloud[CHILD_ID].toString())
//            .updateChildren(mapDataCloud)
//            .addOnCompleteListener { task ->
//            }
//    }

    private fun initialise() {
        REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navControllerMain = navHostFragment.navController
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setupWithNavController(navControllerMain)
//        player = MediaPlayer.create(this, R.raw.alarm)

//        player.setOnPreparedListener {
//            it.start()
//        }
//        player.start()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("AAA", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("AAA", "token: $token")
        })
    }

    override fun onStart() {
        super.onStart()
//        REF_DATABASE_ROOT.child(NODE_USERS)
//            .addChildEventListener(SnapShotChildListener { dataSnapshot ->
//                if (dataSnapshot.exists()) {
//                    val dataCloud = dataSnapshot.getValue(DataCloud::class.java) ?: DataCloud()
//                    when (dataCloud.alarm) {
//                        true -> {
//                            player.start()
//                        }
//                        false -> {
//                            player.seekTo(0)
//                            player.pause()
//                        }
//                    }
//                }
//            })
    }

    private fun observe() {

//        REF_DATABASE_ROOT.child(NODE_USERS)
//            .addChildEventListener(SnapShotChildListener { dataSnapshot ->
//                if (dataSnapshot.exists()) {
//                    val dataCloud = dataSnapshot.getValue(DataCloud::class.java) ?: DataCloud()
//                    if (dataCloud.alarm) {
//                        player.start()
//                        val bundle = bundleOf("dataCloud" to dataCloud)
//                        navControllerMain.navigate(R.id.alarmFragment, bundle)
//                    }
//                }
//            })


//       viewModel.user.observe(this){ dataUi ->
//                if (dataUi.alarm) {
//                    player.start()
//                }
//       }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun onGotLocationPermissionsResult(grantResults: Map<String, Boolean>) {
        if (grantResults.entries.all { it.value }) {
//            onLocationPermissionsGranted()
        } else {
            // example of handling 'Deny & don't ask again' user choice
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                askUserForOpeningAppSettings()
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun askUserForOpeningAppSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        if (packageManager.resolveActivity(
                appSettingsIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            ) == null
        ) {
            Toast.makeText(this, R.string.permissions_denied_forever, Toast.LENGTH_SHORT).show()
        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(R.string.yes) { _, _ ->
                    startActivity(appSettingsIntent)
                }
                .create()
                .show()
        }
    }

    private fun onLocationPermissionsGranted() {
        Toast.makeText(this, R.string.location_permissions_granted, Toast.LENGTH_SHORT)
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun check() {
        requestLocationPermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


//    val usersListener: UsersListener = {
//        it.forEach { dataCloud ->
//            mapDataCloud[CHILD_ID] = dataCloud.id
//            mapDataCloud[CHILD_ID_CACHE] = dataCloud.idCache
//            mapDataCloud[CHILD_FULL_NAME] = dataCloud.fullName
//            mapDataCloud[CHILD_PHONE_USER] = dataCloud.phoneUser
//            mapDataCloud[CHILD_PHONE_OPERATOR] = dataCloud.phoneOperator
//            mapDataCloud[CHILD_PHOTO] = dataCloud.photo
//            mapDataCloud[CHILD_TIME] = dataCloud.time
//            mapDataCloud[CHILD_TIME_LONG] = dataCloud.timeLong
//            mapDataCloud[CHILD_LATITUDE] = dataCloud.latitude
//            mapDataCloud[CHILD_LONGITUDE] = dataCloud.longitude
//            mapDataCloud[CHILD_LOCATION_ADDRESS] = dataCloud.locationAddress
//            mapDataCloud[CHILD_HOME_ADDRESS] = dataCloud.homeAddress
//            mapDataCloud[CHILD_COMPANY] = dataCloud.company
//            mapDataCloud[CHILD_ALARM] = dataCloud.alarm
//            mapDataCloud[CHILD_NOTIFY] = dataCloud.notify
//
//            REF_DATABASE_ROOT.child(NODE_USERS).child(dataCloud.id.toString())
//                .updateChildren(mapDataCloud)
//                .addOnCompleteListener { task ->
//                }
//        }
//    }
}

