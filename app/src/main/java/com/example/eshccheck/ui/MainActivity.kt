package com.example.eshccheck.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eshccheck.R
import com.example.eshccheck.databinding.ActivityMainBinding
import com.example.eshccheck.ui.app.App
import com.example.eshccheck.ui.screens.MainFragment
import com.example.eshccheck.utils.AlarmHandle
import com.example.eshccheck.utils.SnackBuilder
import com.example.eshccheck.utils.connectivity.ConnectivityManager
import com.example.eshccheck.utils.firebase.NODE_USERS
import com.example.eshccheck.utils.firebase.REF_DATABASE_ROOT
import com.example.eshccheck.utils.firebase.UsersService
import com.example.eshccheck.utils.listeners.SnapShotListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainFragment.PermissionHandle {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var snackTopBuilder: SnackBuilder

    private lateinit var binding: ActivityMainBinding
    private lateinit var navControllerMain: NavController
    private lateinit var destinationChangedListener:
            NavController.OnDestinationChangedListener
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var snack: Snackbar

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestLocationPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),   // contract for requesting more than 1 permission
        ::onGotLocationPermissionsResult
    )

    private val usersService: UsersService
        get() = (applicationContext as App).usersService
    private val mapDataCloud = mutableMapOf<String, Any>()

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

        snack = snackTopBuilder.buildSnackTopIndefinite(binding.root.rootView)
        setUpFireBase()
        setUpNavController()
//        observe()
//        usersService.addListener(usersListener)
//        createUser()
    }

    private fun createUser() {
//        REF_DATABASE_ROOT.child(NODE_USERS).child(mapDataCloud[CHILD_ID].toString())
//            .updateChildren(mapDataCloud)
//            .addOnCompleteListener { task ->
//            }
    }

    private fun setUpFireBase() {
        REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference

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

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navControllerMain = navHostFragment.navController
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setupWithNavController(navControllerMain)

        destinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.userDetailsFragment -> {
                        bottomNavigationView.visibility = View.GONE
                    }
                    R.id.mainFragment -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                }
            }
    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
        checkNetworks(connectivityManager) { isNetWorkAvailable ->
            when (isNetWorkAvailable) {
                false -> snack.show()
                true -> snack.dismiss()
            }
        }

        val alarmHandle = AlarmHandle.Base(this)
        REF_DATABASE_ROOT.child(NODE_USERS)
            .addValueEventListener(SnapShotListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    alarmHandle.handle(dataSnapshot)
                }
            })
    }

    override fun onResume() {
        super.onResume()
        navControllerMain.addOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onPause() {
        super.onPause()
        navControllerMain.removeOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
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

    private fun checkNetworks(
        connectivityManager: ConnectivityManager,
        connected: (Boolean) -> Unit
    ): Boolean {
        var isNetWorkAvailable = true
        connectivityManager.isNetworkAvailable.observe(this) {
            isNetWorkAvailable = it
            connected(isNetWorkAvailable)
        }
        return isNetWorkAvailable
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

