package com.dyippay.util

import android.content.Context
import android.content.SharedPreferences
import java.util.Date

const val PREF_MAIN = "appetizercodingchallenge"
const val PREF_LAST_USER_VISITED = "com.appetizercodingchallenge.LAST_USER_VISITED"
const val PREF_BEARER_TOKEN = "com.appetizercodingchallenge.BEARER_TOKEN"

fun Context.getPref(): SharedPreferences =
    getSharedPreferences(PREF_MAIN, Context.MODE_PRIVATE)

fun SharedPreferences.getBearerToken(): String? =
    getString(PREF_BEARER_TOKEN, null)

fun SharedPreferences.recordPreviousVisited() =
    edit().putLong(PREF_LAST_USER_VISITED, System.currentTimeMillis()).commit()

fun SharedPreferences.setLastUserVisitedTime() =
    edit().putLong(PREF_LAST_USER_VISITED, System.currentTimeMillis()).apply()

fun SharedPreferences.getLastUserVisitedTime(): Date? =
    if (contains(PREF_LAST_USER_VISITED)) Date(
        getLong(
            PREF_LAST_USER_VISITED,
            System.currentTimeMillis()
        )
    )
    else null

fun SharedPreferences.isLoggedIn() =
    !(getString(PREF_LAST_USER_VISITED, null).isNullOrBlank())

fun SharedPreferences.clear() =
    edit().clear().commit()
