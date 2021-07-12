package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
	private lateinit var builder: NotificationCompat.Builder
	private lateinit var notificationManager: NotificationManager
	private lateinit var remoteViews: RemoteViews
	private lateinit var builderRemote: NotificationCompat.Builder
	private lateinit var notificationManagerRemote: NotificationManager
	private lateinit var remoteViewsMain: RemoteViews
	private lateinit var context: Context
	val CHANNEL_ID = "Channel_1"
	val CHANNEL_NAME = "MY_CHANNEL"
	val descriptionText = "MyDescription"
	val bool = Build.VERSION.SDK_INT < Build.VERSION_CODES.O

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		context = this
		val button: Button = findViewById(R.id.button)
		if(bool){
			notifyForUnderOreo()
		}
		else{
			notifyForUnderOreo()
		}
		button.setOnClickListener {
			if(bool)
			{
				notificationManager.notify(System.currentTimeMillis().toInt(),builder.build())
			}
			else
			{
				/*with(NotificationManagerCompat.from(this)) {
					// notificationId is a unique int for each notification that you must define
					notify(8, builder.build())
				}
				notificationManager.notify(9,builder.build())*/
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					val name = "Channel 1"
					val descriptionText = "This is the channel 1"
					val importance = NotificationManager.IMPORTANCE_DEFAULT
					val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
						description = descriptionText
					}
					// Register the channel with the system
					val notificationManager: NotificationManager =
						getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
					notificationManager.createNotificationChannel(channel)
				}
				notificationManager.notify(System.currentTimeMillis().toInt(),builder.build())
			}
		}
	}
	private fun notifyForOverOreo() {
		createNotificationChannel()
		initializeBuilder()
		createRemoteViewsNotification()
	}

	private fun notifyForUnderOreo() {
		mainRemoteViews()
		val notificationIntent = Intent(context,MainActivity::class.java)
		val pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT)
		builder = NotificationCompat.Builder(context,"Channel_1")
		builder.setSmallIcon(R.drawable.notify_asset)
			.setAutoCancel(true)
			.setCustomBigContentView(remoteViewsMain)
			.setContentIntent(pendingIntent)
	}

	private fun mainRemoteViews() {
		notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		remoteViewsMain = RemoteViews(packageName,R.layout.notification)
		remoteViewsMain.setImageViewResource(R.id.imageView,R.mipmap.ic_launcher_round)
		remoteViewsMain.setTextViewText(R.id.text_view,"Someone has requested to follow you")
		val intent = Intent("button_clicked")
		val notification_id = System.currentTimeMillis().toInt()
		intent.putExtra("id",notification_id)
		val p_button = PendingIntent.getBroadcast(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT)
		remoteViewsMain.setOnClickPendingIntent(R.id.button,p_button)
	}

	private fun initializeBuilder() {
		builder = NotificationCompat.Builder(this, CHANNEL_ID)
			.setSmallIcon(R.drawable.notify_asset)
			.setContentTitle("My notification")
			.setProgress(100,5,true)
			.setContentText("Much longer text that cannot fit one line...")
			.setPriority(NotificationCompat.PRIORITY_HIGH)
			.setStyle(NotificationCompat.BigTextStyle()
				.bigText("Much longer text that cannot fit one line..."))
			.setAutoCancel(true)
	}

	private fun createRemoteViewsNotification() {
		remoteViews = RemoteViews(packageName,R.layout.notification)
	}

	private fun createNotificationChannel() {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val name = CHANNEL_NAME
			val importance = NotificationManager.IMPORTANCE_DEFAULT
			val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
				description = descriptionText
			}
			// Register the channel with the system
			notificationManager =
				getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.createNotificationChannel(channel)
		}
	}
	/*private fun createNotificationChannel() {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val name = getString(R.string.channel_name)
			val descriptionText = getString(R.string.channel_description)
			val importance = NotificationManager.IMPORTANCE_DEFAULT
			val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
				description = descriptionText
			}
			// Register the channel with the system
			val notificationManager: NotificationManager =
				getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.createNotificationChannel(channel)
		}
	}*/
}