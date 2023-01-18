package com.kamrulhasan.topnews.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.kamrulhasan.topnews.databinding.FragmentWebViewBinding
import com.kamrulhasan.topnews.utils.DEFAULT_NEWS_PAGE
import com.kamrulhasan.topnews.utils.URL_KEY

private const val TAG = "WebViewFragment"

class WebViewFragment : Fragment() {

    private var _binding : FragmentWebViewBinding? = null
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
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWebViewBinding.inflate(layoutInflater)

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: url $webUrl")

        binding.webView.webViewClient = WebViewClient()

        // this will load the url of the website
        binding.webView.loadUrl(webUrl)

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)
    }

//    fun onBackPressed() {
//        // if your webview can go back it will go back
//        if (binding.webView.canGoBack())
//            binding.webView.goBack()
//        // if your webview cannot go back
//        // it will exit the application
//        else
//            super.onBackPressed()
//    }
}