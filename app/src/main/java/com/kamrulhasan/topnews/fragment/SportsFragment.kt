package com.kamrulhasan.topnews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.databinding.FragmentSportsBinding
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel

class SportsFragment : Fragment() {

    private var _binding : FragmentSportsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSportsBinding.inflate(layoutInflater)
        return binding.root             //inflater.inflate(R.layout.fragment_sports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}