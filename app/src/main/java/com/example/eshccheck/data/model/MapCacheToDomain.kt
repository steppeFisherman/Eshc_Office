package com.example.eshccheck.data.model

import com.example.eshccheck.data.model.cacheModel.DataCache
import com.example.eshccheck.domain.model.DataDomain

interface MapCacheToDomain {

    fun mapCacheToDomain(dataCache: DataCache): DataDomain

    class Base : MapCacheToDomain {
        override fun mapCacheToDomain(dataCache: DataCache): DataDomain =
            DataDomain(
                id = dataCache.id,
                id_cache = dataCache.id_cache,
                full_name = dataCache.full_name,
                phone_user = dataCache.phone_user,
                phone_operator = dataCache.phone_operator,
                photo = dataCache.photo,
                time_location = dataCache.time_location,
                latitude = dataCache.latitude,
                longitude = dataCache.longitude,
                alarm = dataCache.alarm,
                notify = dataCache.notify,
            )
    }
}