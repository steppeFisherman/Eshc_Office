package com.example.eshccheck.domain

import com.example.eshccheck.domain.model.ResultUser

interface Repository {
    suspend fun allUsers(): ResultUser
    suspend fun listenUsers()
    val usersCached: ResultUser
    val fetchAlarmed: ResultUser
}