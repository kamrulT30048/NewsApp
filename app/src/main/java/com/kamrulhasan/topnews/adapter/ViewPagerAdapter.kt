package com.kamrulhasan.topnews.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kamrulhasan.topnews.fragment.*
import com.kamrulhasan.topnews.utils.Constrains
import com.kamrulhasan.topnews.utils.VIEW_PAGER_COUNTER

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    :FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return VIEW_PAGER_COUNTER
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> { //Business News
                BusinessFragment()
            }
            2 ->{ //Entertainment News
                EntertainmentFragment()
            }
            3 ->{
                //General News
                GeneralFragment()
            }
            4 ->{
                //Health News
                HealthFragment()
            }
            5 ->{
                //Sports News
                SportsFragment()
            }
            6 ->{
                //Technology news
                TechnologyFragment()
            }else ->{
                // All News Headlines
                TopNewsFragment()
            }

        }
    }
}