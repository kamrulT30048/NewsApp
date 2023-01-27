package com.kamrulhasan.topnews.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.databinding.ActivityMainBinding
import com.kamrulhasan.topnews.worker.SyncDataWorker
import java.util.concurrent.TimeUnit

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        binding.bottomNavBar.setOnItemSelectedListener {
            Log.d(TAG, "onCreate: home fragment1")
            when (it.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.bookMarkFragment -> {
                    navController.navigate(R.id.bookMarkFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
        setPeriodicUpdate()

    }

    private fun setPeriodicUpdate() {
        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(
                SyncDataWorker::class.java,
                300, TimeUnit.MINUTES  ///300 minutes = 5 hours
            )
            .addTag("syncData")
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "syncData",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}