package com.example.eshccheck.domain

import com.example.eshccheck.domain.model.ResultUser

interface Repository {
    suspend fun allUsers(): ResultUser
}