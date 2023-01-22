package com.kamrulhasan.topnews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamrulhasan.topnews.adapter.NewsViewAdapter
import com.kamrulhasan.topnews.databinding.FragmentSportsBinding
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel

class SportsFragment : Fragment() {

    private var _binding : FragmentSportsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TopNewsViewModel
    private lateinit var adapter: NewsViewAdapter

    private var articleList = emptyList<LocalArticle>()
    private var tempArticleList = emptyList<LocalArticle>()
    private var tempArticleMutableList = mutableListOf<LocalArticle>()

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

        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]

        //TODO edit livedata
        viewModel.sportsArticle.observe(viewLifecycleOwner) {
            articleList = it
            tempArticleList = it
            adapter = NewsViewAdapter(requireContext(), tempArticleList, viewModel)
            binding.recyclerView.adapter = adapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}