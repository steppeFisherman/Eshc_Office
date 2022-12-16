package com.example.eshccheck.data.model

import com.example.eshccheck.data.model.cacheModel.DataCache
import com.example.eshccheck.data.model.cloudModel.DataCloud

interface MapCloudToCache {

    fun mapCloudToCache(dataCloud: DataCloud): DataCache

    class Base : MapCloudToCache {
        override fun mapCloudToCache(dataCloud: DataCloud): DataCache =
            DataCache(
                id = dataCloud.id,
                idCache = dataCloud.idCache,
                fullName = dataCloud.fullName,
                phoneUser = dataCloud.phoneUser,
                phoneOperator = dataCloud.phoneOperator,
                photo = dataCloud.photo,
                time = dataCloud.time,
                timeLong = dataCloud.timeLong,
                latitude = dataCloud.latitude,
                longitude = dataCloud.longitude,
                locationAddress = dataCloud.locationAddress,
                homeAddress = dataCloud.homeAddress,
                company = dataCloud.company,
                alarm = dataCloud.alarm,
                notify = dataCloud.notify,
                locationFlagOnly = dataCloud.locationFlagOnly,
                comment = dataCloud.comment
            )
    }
}


