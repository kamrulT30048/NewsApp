package com.kamrulhasan.topnews.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kamrulhasan.topnews.fragment.HomeFragment
import com.kamrulhasan.topnews.fragment.SportsFragment
import com.kamrulhasan.topnews.fragment.TopNewsFragment
import com.kamrulhasan.topnews.fragment.USAFragment
import com.kamrulhasan.topnews.utils.Constrains

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    :FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return Constrains.viewPagerCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> {
                TopNewsFragment()
            }
            2 ->{
                SportsFragment()
            }
            else ->{
                USAFragment()
            }

        }
    }
}