package com.example.eshccheck.domain.model

data class DataDomain(
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
    val notify: Boolean
)