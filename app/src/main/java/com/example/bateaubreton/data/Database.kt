package com.example.bateaubreton.data

import com.example.bateaubreton.data.dao.FuelDao
import com.example.bateaubreton.data.dao.PortDao
import com.example.bateaubreton.data.dao.UserDao

class Database private constructor()
{
    val userDao: UserDao by lazy { UserDao() }
    val fuelDao: FuelDao by lazy { FuelDao() }
    val portDao: PortDao by lazy { PortDao() }

    companion object
    {
        @Volatile private var instance: Database? = null

        fun getInstance() : Database
        {
            return instance ?: synchronized(this) {
                instance ?: Database().also { instance = it }
            }
        }
    }
}