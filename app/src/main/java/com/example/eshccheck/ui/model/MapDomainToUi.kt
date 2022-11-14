package com.example.eshccheck.ui.model

import com.example.eshccheck.domain.model.DataDomain
import com.example.eshccheck.utils.FormatUiPhoneNumber

interface MapDomainToUi {

    fun mapDomainToUi(dataDomain: DataDomain): DataUi

    class Base : MapDomainToUi {

        private val formatUiPhoneNumber = FormatUiPhoneNumber.Base()

        override fun mapDomainToUi(dataDomain: DataDomain): DataUi =
            DataUi(
                id = dataDomain.id,
                id_cache = dataDomain.id_cache,
                full_name = dataDomain.full_name,
                phone_user = formatUiPhoneNumber.modify(dataDomain.phone_user),
                phone_operator = dataDomain.phone_operator,
                photo = dataDomain.photo,
                time_location = dataDomain.time_location,
                latitude = dataDomain.latitude,
                longitude = dataDomain.longitude,
                alarm = dataDomain.alarm,
                notify = dataDomain.notify
            )
    }
}