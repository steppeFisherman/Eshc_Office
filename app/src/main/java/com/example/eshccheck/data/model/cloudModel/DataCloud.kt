package com.example.eshccheck.data.model.cloudModel

data class DataCloud(
    val id_cache: Int = 0,
    val id: Int = 0,
    val full_name: String = "",
    val phone_user: String = "",
    val phone_operator: String = "",
    val photo: String = "",
    val time_location: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val alarm: Boolean = false,
    val notify: Boolean = false
)
