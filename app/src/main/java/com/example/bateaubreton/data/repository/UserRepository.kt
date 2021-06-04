package com.example.bateaubreton.data.repository

import android.util.Log
import com.android.volley.VolleyError
import com.example.bateaubreton.data.dao.UserDao
import com.example.bateaubreton.data.entity.User
import com.example.bateaubreton.data.network.AddUser
import com.example.bateaubreton.data.network.GetUser
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepository private constructor(private val dao: UserDao)
{
    private var _cache: User? = null

//    private suspend fun getData(): User
//    {
//        return _cache ?: dao.getAll().also { _cache = it }
//    }

    suspend fun addUser(user: User) : Boolean
    {
        return suspendCoroutine { continuation ->
            AddUser.execute(object : AddUser.Result {
                override fun onSuccess(data: Int)
                {
                    Log.i("TEST", "data=$data")
                    continuation.resume(true)
                }

                override fun onFailure(error: VolleyError)
                {
                    Log.i("TEST", "error=$error")
                    continuation.resume(false)
                }
            }, user)
        }
    }

    suspend fun addLocalUser(user: User)
    {
        dao.addLocalUser(user)
    }

    suspend fun getLocalUser() : User?
    {
        return dao.getLocalUser()
    }

    suspend fun updateRememberMe(value: Boolean)
    {
        dao.updateRememberMe(value)
    }

    suspend fun getRememberMe() : Boolean
    {
        return dao.getRememberMe()
    }

    suspend fun isUserValid(pseudo: String, password: String) : Boolean
    {
        return suspendCoroutine { continuation ->
            GetUser.execute(object : GetUser.Result {
                override fun onSuccess(data: User)
                {
                    if (pseudo == data.pseudo && password == data.password)
                    {
                        _cache = data
                        continuation.resume(true)
                    }
                    else
                    {
                        continuation.resume(false)
                    }
                }

                override fun onFailure(error: VolleyError)
                {
                    continuation.resume(false)
                }
            }, pseudo, password)
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(dao: UserDao): UserRepository
        {
            return instance ?: synchronized(this) {
                instance ?: UserRepository(dao).also { instance = it }
            }
        }
    }
}