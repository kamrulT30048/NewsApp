package com.kamrulhasan.topnews.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.databinding.FragmentWebViewBinding
import com.kamrulhasan.topnews.utils.DEFAULT_NEWS_PAGE
import com.kamrulhasan.topnews.utils.URL_KEY
import com.kamrulhasan.topnews.utils.verifyAvailableNetwork

private const val TAG = "WebViewFragment"

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var webUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            webUrl = it.getString(URL_KEY, DEFAULT_NEWS_PAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWebViewBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // hide bottom nav bar
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility = View.GONE

        Log.d(TAG, "onViewCreated: url $webUrl")

        binding.webView.webViewClient = WebViewClient()
        binding.webView.visibility = View.VISIBLE
        // this will load the url of the website
        if (verifyAvailableNetwork(requireActivity() as AppCompatActivity)) {
            binding.webView.loadUrl(webUrl)
            binding.ivCloudOff.visibility = View.GONE
            binding.tvCloudOff.visibility = View.GONE
        } else {
            binding.webView.visibility = View.GONE
            binding.ivCloudOff.visibility = View.VISIBLE
            binding.tvCloudOff.visibility = View.VISIBLE
        }

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility = View.VISIBLE
    }
}