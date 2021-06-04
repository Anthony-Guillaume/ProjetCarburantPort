package com.example.bateaubreton

import android.app.Application

class MyApplication : Application()
{
    companion object
    {
        private var _applicationContext: Application? = null
        val applicationContext get() : Application = _applicationContext!!
    }

    override fun onCreate()
    {
        super.onCreate()
        _applicationContext = this
    }
}