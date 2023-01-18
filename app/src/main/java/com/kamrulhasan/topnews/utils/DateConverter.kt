package com.kamrulhasan.topnews.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    companion object {

        @SuppressLint("SimpleDateFormat")
        fun convertLongToDate(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("dd MMM,yyyy HH:mm")
            return format.format(date)
        }

        fun currentTimeToLong(): Long {
            return System.currentTimeMillis()
        }

        @SuppressLint("SimpleDateFormat")
        fun convertStringToLong(date: String): Long {
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            return df.parse(date).time
        }
    }
}