package com.kamrulhasan.topnews.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.adapter.BookmarkAdapter
import com.kamrulhasan.topnews.adapter.NewsViewAdapter
import com.kamrulhasan.topnews.databinding.FragmentBookMarkBinding
import com.kamrulhasan.topnews.databinding.FragmentTopNewsBinding
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel
import java.util.*

class BookMarkFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentBookMarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TopNewsViewModel
    private lateinit var adapter: BookmarkAdapter

    private var articleList = emptyList<BookMarkArticle>()
    private var tempArticleList = emptyList<BookMarkArticle>()
    private var tempArticleMutableList = mutableListOf<BookMarkArticle>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookMarkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]

        viewModel.bookmarkArticle.observe(viewLifecycleOwner) {
            articleList = it
            tempArticleList = it
            adapter = BookmarkAdapter(requireContext(), tempArticleList, viewModel)
            binding.recyclerView.adapter = adapter
//            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu?.findItem(R.id.search_menu)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextChange(newText: String?): Boolean {
        tempArticleMutableList.clear()
        val searchText = newText!!.toLowerCase(Locale.getDefault())
        if (searchText.isNotEmpty()) {
            articleList.forEach {
                if (it.title?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
                    tempArticleMutableList.add(it)
                }
            }
            tempArticleList = tempArticleMutableList
//            binding.recyclerView.adapter?.notifyDataSetChanged()
            adapter = BookmarkAdapter(requireContext(), tempArticleList, viewModel)
            binding.recyclerView.adapter = adapter
//            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        } else {
            tempArticleList = articleList
            adapter = BookmarkAdapter(requireContext(), tempArticleList, viewModel)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}