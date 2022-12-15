package com.example.eshccheck.utils

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.core.content.ContextCompat.startActivity
import com.example.eshccheck.R
import com.example.eshccheck.data.model.cloudModel.DataCloud
import com.example.eshccheck.map.MapsActivityAlarmType
import com.google.firebase.database.DataSnapshot

interface AlarmHandle {

    fun handle(dataSnapshot: DataSnapshot)

    class Base(private val context: Context): AlarmHandle {

//        val player: MediaPlayer = MediaPlayer.create()

        override fun handle(dataSnapshot: DataSnapshot) {
            val dataCloud = dataSnapshot.getValue(DataCloud::class.java) ?: DataCloud()
            when (dataCloud.alarm) {
                true -> {
//                    player.start()
                    val intent = Intent(context, MapsActivityAlarmType::class.java)
                    intent.putExtra("dataCloud", dataCloud)
                    startActivity(context, intent, null)
                }
//                false -> {
//                    player.seekTo(0)
//                    player.pause()
//                }
            }
        }
    }
}



