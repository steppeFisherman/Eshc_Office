package com.example.eshccheck.data.repository

import androidx.lifecycle.map
import com.example.eshccheck.data.model.MapCacheToDomain
import com.example.eshccheck.data.room.AppRoomDao
import com.example.eshccheck.domain.model.ResultUser
import javax.inject.Inject

interface CacheSource {

    fun fetchLocation(): ResultUser
    fun fetchAlarmed(): ResultUser
    fun fetchUserById(id: String): ResultUser
//    suspend fun fetchCachedByDate(timeStart: Long, timeEnd: Long): ResultUser

    class Base @Inject constructor(
        private val appDao: AppRoomDao,
        private val mapperCacheToDomain: MapCacheToDomain,
        private val dispatchers: ToDispatch,
        private val exceptionHandle: ExceptionHandle
    ) : CacheSource {

        override fun fetchLocation(): ResultUser = try {
            val users = appDao.fetchAllUsersLocation(locationFlagOnly = true, false)
            val domain = users.map { listDataCache ->
                listDataCache.map {
                    mapperCacheToDomain.mapCacheToDomain(it)
                }
            }
            ResultUser.SuccessLiveData(domain)
        } catch (e: Exception) {
            exceptionHandle.handle(exception = e)
        }

        override fun fetchAlarmed(): ResultUser = try {
            val users = appDao.fetchAllUsers(alarm = true)
            val domain = users.map { listDataCache ->
                listDataCache.map { mapperCacheToDomain.mapCacheToDomain(it) }
            }
            ResultUser.SuccessLiveData(domain)
        } catch (e: Exception) {
            exceptionHandle.handle(exception = e)
        }

        override fun fetchUserById(id: String): ResultUser = try {
            val users = appDao.fetchUserById(id)
            val domain = users.map { listDataCache ->
                listDataCache.map { mapperCacheToDomain.mapCacheToDomain(it) }
            }
            ResultUser.SuccessLiveData(domain)
        } catch (e: Exception) {
            exceptionHandle.handle(exception = e)
        }

//        override suspend fun fetchCachedByDate(timeStart: Long, timeEnd: Long): ResultUser =try {
//            val users = appDao.fetchUsersByDate(timeStart, timeEnd)
//            val domain = users.map { mapperCacheToDomain.mapCacheToDomain(it) }
//            ResultUser.SuccessList(domain)
//        } catch (e: Exception) {
//            exceptionHandle.handle(exception = e)
//        }
    }
}