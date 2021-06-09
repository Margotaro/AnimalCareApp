package com.example.petproject2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class NotificationHelper {
    companion object {
        fun createNotificationChannel(
            context: Context,
            importance: Int,
            showBadge: Boolean,
            name: String,
            description: String
        ) {
            // 1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                // 2
                val channelId = "${context.packageName}-$name"
                val channel = NotificationChannel(channelId, name, importance)
                channel.description = description
                channel.setShowBadge(showBadge)

                // 3
                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
        }


        fun createNotificationWithData(context: Context, title: String, message: String) {
            val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"
            val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
                setSmallIcon(R.drawable.avatar) // 3
                setContentTitle(title) // 4
                setContentText(message) // 5
                priority = NotificationCompat.PRIORITY_DEFAULT // 7
                setAutoCancel(true) // 8
                val intent = Intent(context, HomePageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
// 2
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
// 3
                setContentIntent(pendingIntent)
            }

            // 1
            val notificationManager = NotificationManagerCompat.from(context)
// 2
            notificationManager.notify(1001, notificationBuilder.build())
        }
    }
}
