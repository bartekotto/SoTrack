package com.development.sotrack.notifications
import android.app.Application

class DiscoveryListHolder() : Application() {
    companion object {
        val discoveryList: DiscoveryList by lazy { DiscoveryList(listOf()) }
    }

}