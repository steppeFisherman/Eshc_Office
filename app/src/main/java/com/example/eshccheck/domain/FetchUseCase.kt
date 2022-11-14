package com.example.eshccheck.domain

import com.example.eshccheck.domain.model.ResultUser
import javax.inject.Inject

interface FetchUseCase {

   suspend fun allUsers(): ResultUser

    class Base @Inject constructor(private val repository: Repository) : FetchUseCase {
        override suspend fun allUsers(): ResultUser =
            repository.allUsers()
    }
}