package com.sdjic.gradnet

import android.app.Application
import android.content.Context

class GradNetApp: Application() {
    companion object{
        lateinit var AppContext : Context
    }
    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidLogger()
//            androidContext(this@GradNetApp)
//        }
        AppContext = this
    }
}