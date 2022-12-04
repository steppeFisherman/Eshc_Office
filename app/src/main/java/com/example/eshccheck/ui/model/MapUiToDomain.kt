package com.example.eshccheck.ui.model

import com.example.eshccheck.domain.model.DataDomain

interface MapUiToDomain {

    fun mapUiToDomain(dataUi: DataUi): DataDomain

    class Base : MapUiToDomain {
        override fun mapUiToDomain(dataUi: DataUi): DataDomain =
            DataDomain(
                id = dataUi.id,
                idCache = dataUi.idCache,
                fullName = dataUi.fullName,
                phoneUser = dataUi.phoneUser,
                phoneOperator = dataUi.phoneOperator,
                photo = dataUi.photo,
                time = dataUi.time,
                timeLong = dataUi.timeLong,
                latitude = dataUi.latitude,
                longitude = dataUi.longitude,
                locationAddress = dataUi.locationAddress,
                homeAddress = dataUi.homeAddress,
                company = dataUi.company,
                alarm = dataUi.alarm,
                notify = dataUi.notify,
            )
    }
}