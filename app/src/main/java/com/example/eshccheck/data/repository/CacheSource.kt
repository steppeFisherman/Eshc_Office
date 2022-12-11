package com.example.eshccheck.data.repository

import androidx.lifecycle.map
import com.example.eshccheck.data.model.MapCacheToDomain
import com.example.eshccheck.data.room.AppRoomDao
import com.example.eshccheck.domain.model.ResultUser
import javax.inject.Inject

interface CacheSource {

    fun fetchCached(): ResultUser
    fun fetchAlarmed(): ResultUser
//    suspend fun fetchCachedByDate(timeStart: Long, timeEnd: Long): ResultUser

    class Base @Inject constructor(
        private val appDao: AppRoomDao,
        private val mapperCacheToDomain: MapCacheToDomain,
        private val dispatchers: ToDispatch,
        private val exceptionHandle: ExceptionHandle
    ) : CacheSource {

        override fun fetchCached(): ResultUser = try {
            val users = appDao.fetchAllUsers()
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
            val users = appDao.fetchAlarmed(alarm = true)
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