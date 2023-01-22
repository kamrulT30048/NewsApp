package com.kamrulhasan.topnews.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kamrulhasan.topnews.adapter.NewsViewAdapter
import com.kamrulhasan.topnews.databinding.FragmentGeneralBinding
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel
import java.util.*

private const val TAG = "GeneralFragment"

class GeneralFragment : Fragment() {

    private var _binding : FragmentGeneralBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TopNewsViewModel
    private lateinit var adapter: NewsViewAdapter

    private var articleList = emptyList<LocalArticle>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGeneralBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]

        viewModel.generalArticle.observe(viewLifecycleOwner){
            articleList = it
            adapter = NewsViewAdapter(requireContext(), articleList, viewModel)
            binding.recyclerView.adapter = adapter
        }

        binding.swipeContainer.setOnRefreshListener {

            viewModel.hitGeneralArticleAPI()
            // on below line we are setting is refreshing to false.
            binding.swipeContainer.isRefreshing = false

            viewModel.hitGeneralArticleAPI()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}