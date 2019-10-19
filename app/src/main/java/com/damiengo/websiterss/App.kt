package com.damiengo.websiterss

import android.app.Application
import android.content.res.Resources

class App : Application() {

    private lateinit var res: Resources

    companion object {
        lateinit var instance: App
        fun getRes(): Resources {
            return instance.res
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        res = resources
    }

}