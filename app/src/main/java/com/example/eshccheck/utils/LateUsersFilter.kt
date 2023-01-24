package com.example.eshccheck.utils

import android.util.Log
import com.example.eshccheck.ui.model.DataUi
import org.joda.time.DateTime

interface LateUsersFilter {

    fun filter(
        allUsersList: List<DataUi>,
        lateList: List<DataUi>,
        threeDaysAgo: Long,
        block: (listLate: List<DataUi>) -> Unit
    )

    class OneDayLateUsers : LateUsersFilter {
        private val usersLateList = mutableListOf<DataUi>()
        private val lateUsersId = mutableListOf<String>()
//        private val threeDaysAgo = DateTime.now().minusDays(1).toDate().time

        override fun filter(
            allUsersList: List<DataUi>,
            lateList: List<DataUi>,
            threeDaysAgo: Long,
            block: (listLate: List<DataUi>) -> Unit
        ) {
            val threeDaysLateUsersList = lateList.filter { it.timeLong < threeDaysAgo }
            threeDaysLateUsersList.forEach { lateUser -> lateUsersId.add(lateUser.id) }
            lateUsersId.toSet().forEach { id ->
                allUsersList.forEach { user ->
                    if (user.id == id) usersLateList.add(user)
                }
            }
            block(usersLateList)
        }
    }

    class TwoDaysLateUsers : LateUsersFilter {
        private val usersLateList = mutableListOf<DataUi>()
        private val lateUsersId = mutableListOf<String>()
//        private val threeDaysAgo = DateTime.now().minusDays(2).toDate().time

        override fun filter(
            allUsersList: List<DataUi>,
            lateList: List<DataUi>,
            threeDaysAgo: Long,
            block: (listLate: List<DataUi>) -> Unit
        ) {
            val threeDaysLateUsersList = lateList.filter { it.timeLong < threeDaysAgo }
            threeDaysLateUsersList.forEach { lateUser -> lateUsersId.add(lateUser.id) }
            lateUsersId.toSet().forEach { id ->
                allUsersList.forEach { user ->
                    if (user.id == id) usersLateList.add(user)
                }
            }
            block(usersLateList)
        }
    }

    class ThreeDaysLateUsers : LateUsersFilter {
        private val usersLateList = mutableListOf<DataUi>()
        private val lateUsersId = mutableListOf<String>()

        override fun filter(
            allUsersList: List<DataUi>,
            lateList: List<DataUi>,
            threeDaysAgo: Long,
            block: (listLate: List<DataUi>) -> Unit
        ) {
            val threeDaysLateUsersList = lateList.filter { it.timeLong < threeDaysAgo }

            threeDaysLateUsersList.forEach {
//                Log.d("BB", "late: ${it.time}, name: ${it.fullName}")
            }

            threeDaysLateUsersList.forEach { lateUser -> lateUsersId.add(lateUser.id) }
            lateUsersId.toSet().forEach { id ->
//                Log.d("BB", "id: ${id}")

                allUsersList.forEach { user ->
                    if (user.id == id) usersLateList.add(user)
                }
            }
            block(usersLateList)
        }
    }
}