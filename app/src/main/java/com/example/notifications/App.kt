package com.example.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat

class App: Application(){
	companion object{
		public const val CHANNEL_1_ID = "CHANNEL_1"
		public const val CHANNEL_2_ID = "CHANNEL_2"
	}
	override fun onCreate() {
		super.onCreate()

		createNotificationChannels()

	}

	private fun createNotificationChannels() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
			val channel1 = NotificationChannel(
				CHANNEL_1_ID,
				"Channel 1",
				NotificationManager.IMPORTANCE_HIGH
			)
			channel1.lightColor = R.color.teal_200
			channel1.description = "This is the channel 1"
			val channel2 = NotificationChannel(
				CHANNEL_2_ID,
				"Channel 2",
				NotificationManager.IMPORTANCE_LOW
			)
			channel2.description = "This is the channel 2"
			val notificationManager = getSystemService(NotificationManager::class.java)
			notificationManager.createNotificationChannel(channel1)
			notificationManager.createNotificationChannel(channel2)
//			notificationManager.createNotificationChannels(listOf(channel1,channel2))
		}
	}
}