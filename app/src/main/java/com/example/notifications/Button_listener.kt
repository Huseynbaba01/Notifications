package com.example.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Button_listener: BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		intent?.extras?.getInt("id")?.let { manager.cancel(it) }
		Toast.makeText(context,"Button Clicked!",Toast.LENGTH_LONG).show()
	}
}