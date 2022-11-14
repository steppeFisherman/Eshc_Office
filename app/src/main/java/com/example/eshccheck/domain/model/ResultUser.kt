package com.example.eshccheck.domain.model

import androidx.lifecycle.LiveData

sealed class ResultUser {
    data class Success(val users: List<DataDomain>) : ResultUser()
    data class Fail(val errorType: ErrorType) : ResultUser()
    data class Loading(val loading: Boolean) : ResultUser()
}
