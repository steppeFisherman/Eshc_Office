package com.example.eshccheck.data.repository


import com.example.eshccheck.domain.Repository
import com.example.eshccheck.domain.model.ResultUser
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val cloudSource: CloudSource,
    private val exceptionHandle: ExceptionHandle
) : Repository {

    override suspend fun allUsers(): ResultUser  = try{
        cloudSource.allUsers()
    }catch (e: Exception){
        exceptionHandle.handle(exception = e)
    }
}