package com.kamrulhasan.topnews.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.databinding.FragmentDetailsBinding
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.utils.URL_KEY
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel

private const val TAG = "DetailsFragment"

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TopNewsViewModel

    private var localArticle: LocalArticle? = null
//    private var articleId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            localArticle = it.getParcelable(URL_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TopNewsViewModel::class.java]

//        localArticle = viewModel.readArticleById(articleId.toString())

        binding.tvDetailsTitle.text = localArticle?.title
        binding.tvAuthor.text = localArticle?.author
        binding.tvDetailsDescription.text = localArticle?.description
        binding.tvDetailsPublishedAt.text = localArticle?.publishedAt

        if (localArticle?.bookmark == true) {
            binding.ivDetailsBookmark.setImageResource(R.drawable.icon_bookmark_added)
        } else {
            binding.ivDetailsBookmark.setImageResource(R.drawable.icon_bookmark)
        }

        Glide
            .with(requireContext())
            .load(localArticle?.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivArticleImage)

        Log.d(TAG, "onViewCreated: details: ${localArticle?.id} ")

        binding.ivDetailsBookmark.setOnClickListener {
////
            if (localArticle?.bookmark == true) {
                val localArticle = LocalArticle(
                    localArticle?.id!!,
                    localArticle?.author,
                    localArticle?.title,
                    localArticle?.description,
                    localArticle?.urlToImage,
                    localArticle?.publishedAt,
                    localArticle?.url,
                    localArticle?.category,
                    false
                )
                viewModel.updateArticle(localArticle)
                binding.ivDetailsBookmark.setImageResource(R.drawable.icon_bookmark)

                val bookmarkArticle = BookMarkArticle(
                    localArticle.id,
                    localArticle.author,
                    localArticle.title,
                    localArticle.description,
                    localArticle.urlToImage,
                    localArticle.publishedAt,
                    localArticle.url,
                    localArticle.category
                )
                viewModel.deleteBookmarkArticle(bookmarkArticle)
            } else {
                val localArticle = LocalArticle(
                    localArticle?.id!!,
                    localArticle?.author,
                    localArticle?.title,
                    localArticle?.description,
                    localArticle?.urlToImage,
                    localArticle?.publishedAt,
                    localArticle?.url,
                    localArticle?.category,
                    true
                )
                viewModel.updateArticle(localArticle)
                binding.ivDetailsBookmark.setImageResource(R.drawable.icon_bookmark_added)
                // add bookmark table
                val bookmarkArticle = BookMarkArticle(
                    localArticle.id,
                    localArticle.author,
                    localArticle.title,
                    localArticle.description,
                    localArticle.urlToImage,
                    localArticle.publishedAt,
                    localArticle.url,
                    localArticle.category
                )
                viewModel.addBookmarkArticle(bookmarkArticle)
            }
        }

        binding.tvSeeMore.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(URL_KEY, localArticle?.url)
            //navigate to web view fragment
            findNavController().navigate(R.id.webViewFragment, bundle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}