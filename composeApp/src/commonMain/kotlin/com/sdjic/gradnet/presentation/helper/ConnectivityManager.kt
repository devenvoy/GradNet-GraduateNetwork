package com.sdjic.gradnet.presentation.helper

import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ConnectivityManager {

    private val connectivity = Connectivity()

    var isConnected: Boolean = false

    init {
        connectivity.start()
        CoroutineScope(Dispatchers.Unconfined).launch {
            connectivity.statusUpdates.collect { status ->
                when (status) {
                    is Connectivity.Status.Connected -> {
                        isConnected = true
                        println("Connected to network")
                    }
                    is Connectivity.Status.Disconnected -> {
                        isConnected = false
                        println("Disconnected from network")
                    }
                }
            }
        }
    }
}