package com.example.eshccheck.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataUi(
    val id_cache: Int,
    val id: Int,
    val full_name: String,
    val phone_user: String,
    val phone_operator: String,
    val photo: String,
    val time_location: String,
    val latitude: String,
    val longitude: String,
    val alarm: Boolean,
    val notify: Boolean,
): Parcelable