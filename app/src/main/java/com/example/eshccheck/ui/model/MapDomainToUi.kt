package com.example.eshccheck.ui.model

import com.example.eshccheck.domain.model.DataDomain
import com.example.eshccheck.utils.FormatUiPhoneNumber

interface MapDomainToUi {

    fun mapDomainToUi(dataDomain: DataDomain): DataUi

    class Base : MapDomainToUi {

        private val formatUiPhoneNumber = FormatUiPhoneNumber.ToUi()

        override fun mapDomainToUi(dataDomain: DataDomain): DataUi =
            DataUi(
                id = dataDomain.id,
                idCache = dataDomain.idCache,
                fullName = dataDomain.fullName,
                phoneUser = formatUiPhoneNumber.modify(dataDomain.phoneUser),
                phoneOperator = dataDomain.phoneOperator,
                photo = dataDomain.photo,
                time = dataDomain.time,
                timeLong = dataDomain.timeLong,
                latitude = dataDomain.latitude,
                longitude = dataDomain.longitude,
                locationAddress = dataDomain.locationAddress,
                homeAddress = dataDomain.homeAddress,
                company = dataDomain.company,
                alarm = dataDomain.alarm,
                notify = dataDomain.notify,
                locationFlagOnly = dataDomain.locationFlagOnly,
                comment = dataDomain.comment
            )
    }
}

