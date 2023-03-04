package com.kamrulhasan.topnews.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kamrulhasan.topnews.fragment.newsApi
import com.kamrulhasan.topnews.utils.MyApplication
import com.kamrulhasan.topnews.utils.makeStatusNotification
import com.kamrulhasan.topnews.utils.verifyAvailableNetwork

private const val TAG = "SyncDataWorker"

class SyncDataWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    @SuppressLint("WrongThread")
    override fun doWork(): Result {
        return try {

            Log.d(TAG, "doWork: called")
            if (verifyAvailableNetwork(MyApplication.appContext)) {
                Log.d(TAG, "doWork: internet is connected")
                // API call
                newsApi()
                makeStatusNotification(
                    "Top News is Updated, Please check",
                    MyApplication.appContext
                )
            } else {
                makeStatusNotification(
                    "Please connect the internet for get Updated News",
                    MyApplication.appContext
                )
            }
            Result.success()
        } catch (e: Exception) {
            makeStatusNotification("API called failed", MyApplication.appContext)
            Result.failure()
        }
    }
}