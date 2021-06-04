package com.example.bateaubreton.data.dao

import android.content.Context
import android.content.SharedPreferences
import com.example.bateaubreton.MyApplication
import com.example.bateaubreton.R
import com.example.bateaubreton.data.entity.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserDao
{
    suspend fun updateRememberMe(value: Boolean)
    {
        val context: Context = MyApplication.applicationContext
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(context.getString(R.string.remember_me_key), value)
            apply()
        }
    }

    suspend fun getRememberMe() : Boolean
    {
        val context: Context = MyApplication.applicationContext
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        return sharedPref.getBoolean(context.getString(R.string.remember_me_key), false)
    }

    suspend fun addLocalUser(user: User)
    {
        val context: Context = MyApplication.applicationContext
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(context.getString(R.string.local_user), Json.encodeToString(user))
            apply()
        }
    }

    suspend fun getLocalUser() : User?
    {
        val context: Context = MyApplication.applicationContext
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        val userJson: String? = sharedPref.getString(context.getString(R.string.local_user), null)
        return userJson?.let { Json.decodeFromString(it) }
    }
}