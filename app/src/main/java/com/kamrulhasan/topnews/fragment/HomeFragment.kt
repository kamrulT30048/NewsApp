package com.kamrulhasan.topnews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.adapter.ViewPagerAdapter
import com.kamrulhasan.topnews.databinding.FragmentHomeBinding
import com.kamrulhasan.topnews.databinding.FragmentSportsBinding
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab,position->
            when(position){
                0 -> tab.text = "USA"
                1 -> tab.text = "TopNews"
                2 -> tab.text = "Spots"
            }
        }.attach()
    }
}