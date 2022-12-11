package com.example.eshccheck.data.model.cloudModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataCloud(
    val id: String = "",
    val idCache: Int = 0,
    val fullName: String = "",
    val phoneUser: String = "",
    val phoneOperator: String = "",
    val photo: String = "",
    val time: String = "",
    val timeLong: Long = 0,
    val latitude: String = "",
    val longitude: String = "",
    val locationAddress: String = "",
    val homeAddress: String = "",
    val company: String = "",
    val alarm: Boolean = false,
    val notify: Boolean = false
): Parcelable
