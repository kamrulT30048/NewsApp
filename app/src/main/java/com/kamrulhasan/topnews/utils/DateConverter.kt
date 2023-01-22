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
            val stringDate = zoneToDate(date)
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return df.parse(stringDate)!!.time
        }

        fun zoneToDate(date: String): String {
            val splitDate = date.split("T", "Z")
            return splitDate[0] + " " + splitDate[1]
        }
    }

}