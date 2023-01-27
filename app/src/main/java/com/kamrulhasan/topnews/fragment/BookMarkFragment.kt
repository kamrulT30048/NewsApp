package com.kamrulhasan.topnews.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.adapter.BookmarkAdapter
import com.kamrulhasan.topnews.databinding.FragmentBookMarkBinding
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.utils.MyApplication
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel
import java.util.*

private const val TAG = "BookMarkFragment"

class BookMarkFragment : Fragment() {
    private var _binding: FragmentBookMarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TopNewsViewModel
    private lateinit var adapter: BookmarkAdapter

    private var articleList = emptyList<BookMarkArticle>()
    private var tempArticleList = emptyList<BookMarkArticle>()
    private var tempArticleMutableList = mutableListOf<BookMarkArticle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d(TAG, "onCreate: bookmark")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookMarkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]

        viewModel.bookmarkArticle.observe(viewLifecycleOwner) {
            articleList = it
            tempArticleList = it
            adapter = BookmarkAdapter(tempArticleList, viewModel)
            binding.recyclerView.adapter = adapter

            if (articleList.isNotEmpty()) {
                binding.tvNoItem.visibility = View.GONE
            } else {
                binding.tvNoItem.visibility = View.VISIBLE
            }
        }
    }

    /// search menu
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search_menu_item)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchArticle(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchArticle(newText.toString())
                return true
            }
        })
    }

    /// search article with search menu
    private fun searchArticle(text: String) {
        tempArticleMutableList.clear()

//        val imm = MyApplication.appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

//        if(imm.isActive){
//            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
//                .visibility = View.GONE
//        }

        val searchText = text.toLowerCase(Locale.getDefault())
        tempArticleList = if (searchText.isNotEmpty()) {
            articleList.forEach {
                if (it.title?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
                    tempArticleMutableList.add(it)
                }
            }
            tempArticleMutableList
        } else {
            articleList
        }
        adapter = BookmarkAdapter(tempArticleList, viewModel)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()

//        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
//            .visibility = View.VISIBLE
//        _binding = null
    }
}