package com.sdjic.gradnet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmk.kmpnotifier.permission.permissionUtil

class MainActivity : ComponentActivity() {
    companion object{
        lateinit var instance: MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        instance = this
        val permissionUtil by permissionUtil()
        permissionUtil.askNotificationPermission()
        setContent {
            App()
        }
    }
}
