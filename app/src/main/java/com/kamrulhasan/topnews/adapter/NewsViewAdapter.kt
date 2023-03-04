package com.kamrulhasan.topnews.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.utils.MyApplication
import com.kamrulhasan.topnews.utils.URL_KEY
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel

class NewsViewAdapter(
    private val newsList: List<LocalArticle>,
    private val viewModel: TopNewsViewModel
) : RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder>() {

    class NewsViewHolder(binding: View) : RecyclerView.ViewHolder(binding.rootView) {

        val publishedDate: TextView = binding.findViewById(R.id.tv_publish_date)
        val title: TextView = binding.findViewById(R.id.tv_news_title)
        val desc: TextView = binding.findViewById(R.id.tv_news_description)
        val img: ImageView = binding.findViewById(R.id.iv_news_photo)
        val bookmark: ImageView = binding.findViewById(R.id.iv_bookmark)
        val details: TextView = binding.findViewById(R.id.tv_see_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(MyApplication.appContext).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val newsItem = newsList[position]

        holder.publishedDate.text = newsItem.publishedAt
        holder.title.text = newsItem.title
        holder.desc.text = newsItem.description

        //loading news image with glide
        Glide
            .with(holder.itemView.context)
            .load(newsItem.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.icon_loading)
            .into(holder.img)

        if (newsItem.bookmark) {
            holder.bookmark.setImageResource(R.drawable.icon_bookmark_added)
        } else {
            holder.bookmark.setImageResource(R.drawable.icon_bookmark)
        }

        holder.details.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(URL_KEY, newsItem)
            //navigate to web view fragment
            holder.itemView.findNavController().navigate(R.id.detailsFragment, bundle)
        }

        holder.bookmark.setOnClickListener {
            val localUrl = newsItem.url
            if (newsItem.bookmark) {
                viewModel.updateArticle(localUrl, false)
            } else {
                viewModel.updateArticle(localUrl, true)

                // add bookmark table
                val bookmarkArticle = BookMarkArticle(
                    newsItem.author,
                    newsItem.title,
                    newsItem.description,
                    newsItem.urlToImage,
                    newsItem.publishedAt,
                    newsItem.url,
                    newsItem.category
                )
                viewModel.addBookmarkArticle(bookmarkArticle)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}