package com.daydreamapplications.gemgame.utils

import android.content.SharedPreferences

open class NullSharedPreferences : SharedPreferences {

    override fun getAll(): MutableMap<String, *> = TODO("Not yet implemented")
    override fun getString(key: String?, defValue: String?): String? = TODO("Not yet implemented")
    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? = TODO("Not yet implemented")
    override fun getInt(key: String?, defValue: Int): Int = TODO("Not yet implemented")
    override fun getLong(key: String?, defValue: Long): Long = TODO("Not yet implemented")
    override fun getFloat(key: String?, defValue: Float): Float = TODO("Not yet implemented")
    override fun getBoolean(key: String?, defValue: Boolean): Boolean = TODO("Not yet implemented")
    override fun contains(key: String?): Boolean = TODO("Not yet implemented")
    override fun edit(): SharedPreferences.Editor = TODO("Not yet implemented")
    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?): Unit = TODO("Not yet implemented")
    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?): Unit = TODO("Not yet implemented")
}