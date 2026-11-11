package com.crafted.castle.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.crafted.castle.service.VideoUpdateService

class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Start update service after boot
            val serviceIntent = Intent(context, VideoUpdateService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
        }
    }
}