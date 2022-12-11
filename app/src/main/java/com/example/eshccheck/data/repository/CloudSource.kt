package com.example.eshccheck.data.repository

import com.example.eshccheck.data.model.MapCacheToDomain
import com.example.eshccheck.data.model.MapCloudToCache
import com.example.eshccheck.data.model.MapCloudToDomain
import com.example.eshccheck.data.model.cloudModel.DataCloud
import com.example.eshccheck.data.room.AppRoomDao
import com.example.eshccheck.domain.model.ResultUser
import com.example.eshccheck.utils.NODE_USERS
import com.example.eshccheck.utils.REF_DATABASE_ROOT
import com.example.eshccheck.utils.SnapShotChildListener
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface CloudSource {

    suspend fun allUsers(): ResultUser
    suspend fun listenUsers()

    class Base @Inject constructor(
        private val appDao: AppRoomDao,
        private val mapperCacheToDomain: MapCacheToDomain,
        private val mapperCloudToCache: MapCloudToCache,
        private val mapperCloudToDomain: MapCloudToDomain,
        private val dispatchers: ToDispatch,
        private val exceptionHandle: ExceptionHandle
    ) : CloudSource {

        private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
        private val scope = CoroutineScope(Job() + exceptionHandler)

        private lateinit var result: ResultUser
        private lateinit var resultUser: ResultUser

        override suspend fun allUsers(): ResultUser {
            REF_DATABASE_ROOT.child(NODE_USERS).get()
                .addOnCompleteListener() { task ->
                    result = if (task.isSuccessful) {
                        val dataCloudList = task.result.children.map { dataSnapshot ->
                            dataSnapshot.getValue(DataCloud::class.java) ?: DataCloud()
                        }

                        val dataDomainList = dataCloudList.map { dataCloud ->
                            mapperCloudToDomain.mapCloudToDomain(dataCloud)
                        }

                        ResultUser.SuccessList(dataDomainList)
                    } else exceptionHandle.handle(exception = task.exception)
                }
                .addOnFailureListener {
                    exceptionHandle.handle(exception = it)
                }.await()
            return result
        }

        override suspend fun listenUsers() {
            REF_DATABASE_ROOT.child(NODE_USERS)
                .addChildEventListener(SnapShotChildListener { dataSnapshot ->
                    if (dataSnapshot.exists()) {
                        val dataCloud = dataSnapshot.getValue(DataCloud::class.java) ?: DataCloud()
                        val dataCache = mapperCloudToCache.mapCloudToCache(dataCloud)
                        dispatchers.launchIO(scope = scope) { appDao.insertItem(dataCache) }
                    }
                })
        }
    }
}

