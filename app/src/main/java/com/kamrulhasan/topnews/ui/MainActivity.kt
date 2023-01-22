package com.kamrulhasan.topnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyboardShortcutGroup
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.adapter.ViewPagerAdapter
import com.kamrulhasan.topnews.databinding.ActivityMainBinding
import com.kamrulhasan.topnews.fragment.BookMarkFragment
import com.kamrulhasan.topnews.fragment.HomeFragment
import com.kamrulhasan.topnews.utils.DateConverter
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val date = "2023-01-04T15:45:32Z"
        Log.d(TAG, "onCreate: ${DateConverter.convertStringToLong(date)}")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        binding.bottomNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.bookMarkFragment ->  {
                    navController.navigate(R.id.bookMarkFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.search_menu,menu)
//        val item = menu?.findItem(R.id.search_menu)
//        val searchView = item?.actionView as SearchView
//
//        searchView.setOnQueryTextListener(
//            object : SearchView.OnQueryTextListener{
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return false
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//
//                    return false
//                }
//
//            }
//        )
//
//        return super.onCreateOptionsMenu(menu)
//    }

}