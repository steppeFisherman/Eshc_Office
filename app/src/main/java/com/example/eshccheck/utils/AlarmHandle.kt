package com.example.eshccheck.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.eshccheck.data.model.cloudModel.DataCloud
import com.example.eshccheck.map.MapsActivityAlarmType
import com.google.firebase.database.DataSnapshot

interface AlarmHandle {

    fun handle(dataSnapshot: DataSnapshot)

    class Base(private val context: Context) : AlarmHandle {
        override fun handle(dataSnapshot: DataSnapshot) {
            val dataCloudList = dataSnapshot.children.map {
                it.getValue(DataCloud::class.java) ?: DataCloud()
            }
            dataCloudList.forEach { dataCloud ->
                if (dataCloud.alarm) {
                    val intent = Intent(context, MapsActivityAlarmType::class.java)
                    intent.putExtra("dataCloud", dataCloud)
                    startActivity(context, intent, null)
                }
            }
        }
    }
}



