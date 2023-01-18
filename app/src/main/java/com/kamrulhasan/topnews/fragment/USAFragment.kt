package com.kamrulhasan.topnews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.adapter.NewsViewAdapter
import com.kamrulhasan.topnews.databinding.FragmentUSABinding
import com.kamrulhasan.topnews.model.Article
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel


class USAFragment : Fragment() {

    private var _binding : FragmentUSABinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TopNewsViewModel
    private var newsList = emptyList<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUSABinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]

//        val adapter = NewsViewAdapter(requireContext(), newsList)
//        binding.recyclerView.adapter = adapter
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        viewModel.jsonArray.observe(viewLifecycleOwner){
            newsList = it

            val adapter = NewsViewAdapter(requireContext(), newsList)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}