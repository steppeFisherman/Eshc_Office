package com.example.eshccheck.domain

import com.example.eshccheck.domain.model.ResultUser
import javax.inject.Inject

interface FetchUseCase {

    suspend fun allUsers(): ResultUser
    suspend fun listenUsers()
    fun fetchCached(): ResultUser
    fun fetchAlarmed(): ResultUser

    class Base @Inject constructor(private val repository: Repository) : FetchUseCase {
        override suspend fun allUsers(): ResultUser = repository.allUsers()
        override suspend fun listenUsers() = repository.listenUsers()
        override fun fetchCached(): ResultUser = repository.usersCached
        override fun fetchAlarmed(): ResultUser = repository.fetchAlarmed
    }
}