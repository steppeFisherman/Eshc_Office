package com.example.eshccheck.data.model

import com.example.eshccheck.data.model.cacheModel.DataCache
import com.example.eshccheck.domain.model.DataDomain

interface MapCacheToDomain {

    fun mapCacheToDomain(dataCache: DataCache): DataDomain

    class Base : MapCacheToDomain {
        override fun mapCacheToDomain(dataCache: DataCache): DataDomain =
            DataDomain(
                id = dataCache.id,
                idCache = dataCache.idCache,
                fullName = dataCache.fullName,
                phoneUser = dataCache.phoneUser,
                phoneOperator = dataCache.phoneOperator,
                photo = dataCache.photo,
                time = dataCache.time,
                timeLong = dataCache.timeLong,
                latitude = dataCache.latitude,
                longitude = dataCache.longitude,
                locationAddress = dataCache.locationAddress,
                homeAddress = dataCache.homeAddress,
                company = dataCache.company,
                alarm = dataCache.alarm,
                notify = dataCache.notify,
            )
    }
}