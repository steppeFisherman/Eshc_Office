package com.example.eshccheck.map

import android.annotation.SuppressLint
import android.location.Geocoder
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.addTextChangedListener
import com.example.eshccheck.R
import com.example.eshccheck.data.model.cloudModel.DataCloud
import com.example.eshccheck.databinding.ActivityMapsAlarmTypeBinding
import com.example.eshccheck.utils.AlarmHandle
import com.example.eshccheck.utils.TextWatcher
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivityAlarmType : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityMapsAlarmTypeBinding
    private lateinit var mMap: GoogleMap
    private lateinit var geoCoder: Geocoder
    private lateinit var user: DataCloud
    private lateinit var alarmHandle: AlarmHandle
    private lateinit var player: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
        Changes statusBar text/icons color, when the statusBar background is white or transparent
        (this works with API level 21 and higher)
         */
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = true
        binding = ActivityMapsAlarmTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player = MediaPlayer.create(this, R.raw.alarm)
        player.start()

        geoCoder = Geocoder(this, Locale.getDefault())
        val bundle = intent.extras?.get("dataCloud")


        if (bundle != null) user = bundle as DataCloud
        binding.txtId.text = user.id
        binding.txtTime.text = user.time
        binding.txtName.text = user.fullName
        binding.txtPhone.text = user.phoneUser
        binding.btnSoundOff.setOnClickListener {
            player.seekTo(0)
            player.pause()
            it.isEnabled = false
        }

        binding.editTextComment.addTextChangedListener(TextWatcher { textChanged ->
            if (textChanged?.isNotBlank() == true){
                binding.btnSaveComments.isEnabled = true

            } else binding.btnSaveComments.isEnabled = false
        })

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

        val currentLatLong = LatLng(user.latitude.toDouble(), user.longitude.toDouble())
        placeMarkerOnMap(currentLatLong)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 18f))
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val locationAddress = geoCoder
            .getFromLocation(currentLatLong.latitude, currentLatLong.longitude, 2)

        if (locationAddress.isNotEmpty() && locationAddress.size > 1) {
            val address = locationAddress[1].getAddressLine(0)
            val markerOptions = MarkerOptions().position(currentLatLong)
            markerOptions.title(address)
            mMap.addMarker(markerOptions)
        }
    }

    override fun onMarkerClick(p0: Marker) = false

    override fun onBackPressed() {
        if (binding.editTextComment.text?.isBlank() == true){
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.comments_not_added))
                .setMessage("Добавить?")
                .setPositiveButton(R.string.yes) { _, _ ->
                    binding.editTextComment.requestFocus()
                }
                .create()
                .show()
        }
    }
}

