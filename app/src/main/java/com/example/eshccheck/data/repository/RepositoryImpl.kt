package com.example.eshccheck.data.repository

import com.example.eshccheck.domain.Repository
import com.example.eshccheck.domain.model.ResultUser
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val cloudSource: CloudSource,
    private val cacheSource: CacheSource,
    private val exceptionHandle: ExceptionHandle
) : Repository {

    override suspend fun allUsers(): ResultUser = try {
        cloudSource.allUsers()
    } catch (e: Exception) {
        exceptionHandle.handle(exception = e)
    }

    override suspend fun listenUsers() {
        try {
            cloudSource.listenUsers()
        } catch (e: Exception) {
            exceptionHandle.handle(exception = e)
        }
    }

    override val usersLocation: ResultUser
        get() = try {
            cacheSource.fetchLocation()
        } catch (e: Exception) {
            exceptionHandle.handle(exception = e)
        }

    override val fetchAlarmed: ResultUser
        get() = try {
            cacheSource.fetchAlarmed()
        } catch (e: Exception) {
            exceptionHandle.handle(exception = e)
        }

    override val fetchLate: ResultUser = try {
        cacheSource.fetchLate()
    } catch (e: Exception) {
        exceptionHandle.handle(exception = e)
    }

    override fun fetchUserById(id: String): ResultUser = try {
        cacheSource.fetchUserById(id = id)
    } catch (e: Exception) {
        exceptionHandle.handle(exception = e)
    }
}