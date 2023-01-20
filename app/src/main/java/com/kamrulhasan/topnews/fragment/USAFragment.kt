package com.kamrulhasan.topnews.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamrulhasan.topnews.adapter.NewsViewAdapter
import com.kamrulhasan.topnews.databinding.FragmentUSABinding
import com.kamrulhasan.topnews.model.Article
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel
import java.util.*
import kotlin.math.log

private const val TAG = "USAFragment"

class USAFragment : Fragment() {

    private var _binding : FragmentUSABinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TopNewsViewModel
    private lateinit var adapter: NewsViewAdapter

    private var articleList = emptyList<LocalArticle>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUSABinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]

        viewModel.usaArticle.observe(viewLifecycleOwner){
            articleList = it
            adapter = NewsViewAdapter(requireContext(), articleList, viewModel)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.swipeContainer.setOnRefreshListener {

            // on below line we are setting is refreshing to false.
            binding.swipeContainer.isRefreshing = false

            viewModel.hitUsaArticleAPI()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}