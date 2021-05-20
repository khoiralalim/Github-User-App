package com.alimmanurung.submission.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.alimmanurung.submission.R
import com.alimmanurung.submission.view.MainActivity
import java.util.*

class Reminder : BroadcastReceiver() {
    companion object {
        private const val REPEAT_ID = 100
        private const val NOTIFICATION_ID = 100
        var ID_CHANEL = "channel_01"
        var NAME_CHANEL: CharSequence = "reminder"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        alarmNotif(context!!)
    }

    fun alarmRepeat(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Reminder::class.java)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context,
            REPEAT_ID, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, context.getString(R.string.repeatsuccess), Toast.LENGTH_SHORT).show()
    }

    fun alarmCancel(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Reminder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,
            REPEAT_ID, intent, 0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, context.getString(R.string.RepeatCancel), Toast.LENGTH_SHORT).show()
    }

    fun alarmNotif(context: Context) {
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, ID_CHANEL
        )
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.reminder))
            .setContentText("Reminder 09.00 AM")
            .setSound(alarmSound)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ID_CHANEL,
                NAME_CHANEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(ID_CHANEL)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(NOTIFICATION_ID, notification)
    }
}