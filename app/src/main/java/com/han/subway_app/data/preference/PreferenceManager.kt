package com.han.subway_app.data.preference

interface PreferenceManager {
    fun getLong(key: String): Long?
    fun putLong(key: String, value: Long)
}