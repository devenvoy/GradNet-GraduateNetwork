package com.sdjic.gradnet

import android.app.Application
import android.content.Context
import android.util.Log
import com.amitshekhar.DebugDB
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration

class GradNetApp : Application() {
    companion object {
        lateinit var AppContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        AppContext = this
        Log.e("DebugDB", "onCreate: ${DebugDB.getAddressLog()}")
        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_launcher_foreground,
                showPushNotification = true,
            )
        )
    }
}