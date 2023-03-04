package com.kamrulhasan.topnews.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.adapter.BookmarkAdapter
import com.kamrulhasan.topnews.adapter.NewsViewAdapter
import com.kamrulhasan.topnews.databinding.FragmentTopNewsBinding
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.utils.CHECK_INTERNET
import com.kamrulhasan.topnews.utils.MyApplication
import com.kamrulhasan.topnews.utils.verifyAvailableNetwork
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel
import java.util.*

private lateinit var viewModel: TopNewsViewModel

class TopNewsFragment : Fragment() {

    private var _binding: FragmentTopNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NewsViewAdapter

    private var articleList = emptyList<LocalArticle>()
    private var tempArticleMutableList  = mutableListOf<LocalArticle>()
    private var tempArticleList = articleList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTopNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]
        val connection = verifyAvailableNetwork(requireActivity() as AppCompatActivity)

        viewModel.allArticle.observe(viewLifecycleOwner) {
            articleList = it
            tempArticleList = it

            //save recyclerview state
            val adapterViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
            binding.recyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)

            adapter = NewsViewAdapter(tempArticleList, viewModel)
            binding.recyclerView.adapter = adapter
            if (articleList.isNotEmpty()) {
                binding.ivCloudOff.visibility = View.GONE
                binding.tvCloudOff.visibility = View.GONE
            }
        }

        if (articleList.isEmpty() && !connection) {
            binding.ivCloudOff.setImageResource(R.drawable.icon_cloud_off_24)
            binding.ivCloudOff.visibility = View.VISIBLE
            binding.tvCloudOff.visibility = View.VISIBLE
        } else if (articleList.isEmpty()) {
            binding.ivCloudOff.setImageResource(R.drawable.icon_loading)
            binding.ivCloudOff.visibility = View.VISIBLE
            binding.tvCloudOff.visibility = View.GONE
            viewModel.realAllArticle()

            //handle data loading error
            Handler(Looper.getMainLooper()).postDelayed({
                if(articleList.isEmpty()){
                    binding.ivCloudOff.setImageResource(R.drawable.icon_sync_problem_24)
                    binding.ivCloudOff.visibility = View.VISIBLE
                    Toast.makeText(MyApplication.appContext, "Data Sync Failed,\n" +
                            " Refresh Again!!", Toast.LENGTH_SHORT).show()
                }
            }, 5000)
        }

        // pull refresh
        binding.swipeContainer.setOnRefreshListener {

            if (articleList.isEmpty() && !connection) {
                binding.ivCloudOff.setImageResource(R.drawable.icon_cloud_off_24)
                binding.ivCloudOff.visibility = View.VISIBLE
                binding.tvCloudOff.visibility = View.VISIBLE
            } else if (articleList.isEmpty()) {
                binding.ivCloudOff.setImageResource(R.drawable.icon_loading)
                binding.ivCloudOff.visibility = View.VISIBLE
                binding.tvCloudOff.visibility = View.GONE
                viewModel.hitServer()

                //handle data loading error
                Handler(Looper.getMainLooper()).postDelayed({
                    if(articleList.isEmpty()){
                        binding.ivCloudOff.setImageResource(R.drawable.icon_sync_problem_24)
                        binding.ivCloudOff.visibility = View.VISIBLE
                        Toast.makeText(MyApplication.appContext, "Data Sync Failed,\n" +
                                " Refresh Again!!", Toast.LENGTH_SHORT).show()
                    }
                }, 5000)
            } else if (!connection) {
                Toast.makeText(
                    requireContext(),
                    CHECK_INTERNET,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.hitServer()
                binding.ivCloudOff.visibility = View.GONE
                binding.tvCloudOff.visibility = View.GONE
            }
            // on below line we are setting is refreshing to false.
            binding.swipeContainer.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
        //save recyclerview state
        val adapterViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
        binding.recyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)

        adapter = NewsViewAdapter(tempArticleList, viewModel)
        binding.recyclerView.adapter = adapter
    }
}

/// API call function for periodic worker
fun newsApi() {
//    viewModel.hitServer()
}
