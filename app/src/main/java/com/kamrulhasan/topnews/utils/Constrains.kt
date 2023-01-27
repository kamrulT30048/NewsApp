package com.kamrulhasan.topnews.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kamrulhasan.topnews.BuildConfig
import com.kamrulhasan.topnews.R

//By Default Image
const val IMAGE_URL = "https://cdn-icons-png.flaticon.com/512/4416/4416314.png"

//Web View Fragment
const val URL_KEY = "NEWS_URL_KEY"
const val DEFAULT_NEWS_PAGE = "https://www.bbc.com/bengali"

//Fragment category key
const val GENERAL_ARTICLE = "general"
const val BUSINESS_ARTICLE = "business"
const val ENTERTAINMENT_ARTICLE = "entertainment"
const val SPORTS_ARTICLE = "sports"
const val HEALTH_ARTICLE = "health"
const val TECHNOLOGY_ARTICLE = "technology"

const val VIEW_PAGER_COUNTER = 7

////  API url  \\\\
const val BASE_URL = "https://newsapi.org/v2/"

/// API Key
const val api_key = "2bd7895cc96e4a88bb0b58f85b4bca0d"//BuildConfig.apiKey

//GET GENERAL Article API
const val GET_GENERAL_ARTICLE =
    "top-headlines?category=general&apiKey=$api_key"//${BuildConfig.apiKey}"

//GET Business API
const val GET_BUSINESS_ARTICLE = "top-headlines?category=business&apiKey=$api_key"

//GET Entertainment API
const val GET_ENTERTAINMENT_ARTICLE = "top-headlines?category=entertainment&apiKey=$api_key"

//GET Health API
const val GET_HEALTH_ARTICLE = "top-headlines?category=health&apiKey=$api_key"

//GET Sports API
const val GET_SPORTS_ARTICLE = "top-headlines?category=sports&apiKey=$api_key"

//GET Technology API
const val GET_TECHNOLOGY_ARTICLE = "top-headlines?category=technology&apiKey=$api_key"


// internet connectivity error massage
const val CHECK_INTERNET = "Please check your internet connection to reload!!"

// Notification Channel constants

// Name of Notification Channel for verbose notifications of background work
@JvmField
val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"

@JvmField
val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

private const val TAG = "Constrains"

/// network check
@SuppressLint("MissingPermission")
fun verifyAvailableNetwork(activity: Context): Boolean {
    val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo

    return networkInfo != null && networkInfo.isConnected
}

/// notification
@SuppressLint("MissingPermission")
fun makeStatusNotification(message: String, context: Context) {

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    Log.d(TAG, "makeStatusNotification: $message")
    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_forground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    try {
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    } catch (e: Exception) {
        Log.d(TAG, "makeStatusNotification: $e")
    }
}
