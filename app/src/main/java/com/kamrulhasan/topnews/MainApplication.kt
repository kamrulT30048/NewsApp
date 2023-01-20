package com.kamrulhasan.topnews

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        
    }

    companion object {

        lateinit  var appContext: Context
    }
}