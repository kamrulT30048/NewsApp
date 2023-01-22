package com.kamrulhasan.topnews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.adapter.NewsViewAdapter
import com.kamrulhasan.topnews.databinding.FragmentTopNewsBinding
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.utils.CHECK_INTERNET
import com.kamrulhasan.topnews.utils.verifyAvailableNetwork
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel

class TopNewsFragment : Fragment() {

    private var _binding : FragmentTopNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TopNewsViewModel
    private lateinit var adapter: NewsViewAdapter

    private var articleList = emptyList<LocalArticle>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]

        val connection = verifyAvailableNetwork(requireActivity() as AppCompatActivity)
        if(articleList.isEmpty() && !connection){
            binding.ivCloudOff.setImageResource(R.drawable.icon_cloud_off_24)
            binding.ivCloudOff.visibility = View.VISIBLE
            binding.tvCloudOff.visibility = View.VISIBLE
        }else if(articleList.isEmpty()){
            binding.ivCloudOff.setImageResource(R.drawable.icon_loading_buffering)
            binding.ivCloudOff.visibility = View.VISIBLE
            binding.tvCloudOff.visibility = View.GONE
            viewModel.hitServer()
        }

        viewModel.allArticle.observe(viewLifecycleOwner){
            articleList = it
            adapter = NewsViewAdapter(requireContext(), articleList, viewModel)
            binding.recyclerView.adapter = adapter
            if (articleList.isNotEmpty()){
                binding.ivCloudOff.visibility = View.GONE
                binding.tvCloudOff.visibility = View.GONE
            }
        }

        binding.swipeContainer.setOnRefreshListener {
            //todo
            if(articleList.isEmpty() && !connection){
                binding.ivCloudOff.setImageResource(R.drawable.icon_cloud_off_24)
                binding.ivCloudOff.visibility = View.VISIBLE
                binding.tvCloudOff.visibility = View.VISIBLE
            }else if(articleList.isEmpty()){
                binding.ivCloudOff.setImageResource(R.drawable.icon_loading_buffering)
                binding.ivCloudOff.visibility = View.VISIBLE
                binding.tvCloudOff.visibility = View.GONE
            }else if(!connection){
                Toast.makeText(
                    requireContext(),
                    CHECK_INTERNET,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
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
}