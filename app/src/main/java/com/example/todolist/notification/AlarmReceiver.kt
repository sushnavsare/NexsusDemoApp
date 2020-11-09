package com.example.todolist.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import java.lang.Exception

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = Intent(context, NotificationService::class.java)
        service.putExtra("reason", intent.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))

        /**
         * A workaround for android versions greater than or equal to 26
         * without this check, notification cannot be sent and app crashes.
         *
         * [WorkManager] can also be used here.
         */
        try {
            context.startService(service)
        }catch (e : Exception){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(service)
            } else {
                context.startService(service)
            }
        }

    }
}