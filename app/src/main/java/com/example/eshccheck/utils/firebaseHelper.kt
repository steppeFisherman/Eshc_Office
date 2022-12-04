package com.example.eshccheck.utils

import com.google.firebase.database.DatabaseReference

//lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference

const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_ID_CACHE = "idCache"
const val CHILD_FULL_NAME = "fullName"
const val CHILD_PHONE_USER = "phoneUser"
const val CHILD_PHONE_OPERATOR = "phoneOperator"
const val CHILD_PHOTO = "photo"
const val CHILD_TIME = "time"
const val CHILD_TIME_LONG = "timeLong"
const val CHILD_LATITUDE = "latitude"
const val CHILD_LONGITUDE = "longitude"
const val CHILD_LOCATION_ADDRESS = "locationAddress"
const val CHILD_HOME_ADDRESS = "homeAddress"
const val CHILD_COMPANY = "company"
const val CHILD_ALARM = "alarm"
const val CHILD_NOTIFY = "notify"
const val APP_PREFERENCES = "APP_PREFERENCES"
const val PREF_BOOLEAN_VALUE = "PREF_BOOLEAN_VALUE"
const val PREF_PHONE_VALUE = "PREF_PHONE_VALUE"
const val PREF_ID_VALUE = "PREF_ID_VALUE"

//fun  initFirebase(){
//    AUTH = FirebaseAuth.getInstance()
//    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
//}