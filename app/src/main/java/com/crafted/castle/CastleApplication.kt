package com.crafted.castle

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CastleApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    
    companion object {
        lateinit var instance: CastleApplication
            private set
    }
}