package com.sdjic.gradnet

import android.app.Application
import android.content.Context
import android.util.Log
import com.amitshekhar.DebugDB

class GradNetApp : Application() {
    companion object {
        lateinit var AppContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        AppContext = this
        Log.e("DebugDB", "onCreate: ${DebugDB.getAddressLog()}")
    }
}