package com.kamrulhasan.topnews.adapter

import android.content.Context
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
import com.kamrulhasan.topnews.utils.DateConverter
import com.kamrulhasan.topnews.utils.URL_KEY
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel


class BookmarkAdapter(
    private val context: Context,
    private val newsList: List<BookMarkArticle>,
    private val viewModel: TopNewsViewModel
): RecyclerView.Adapter<BookmarkAdapter.NewsViewHolder>() {

    class NewsViewHolder(binding: View) : RecyclerView.ViewHolder(binding.rootView){

        val publishedDate = binding.findViewById<TextView>(R.id.tv_publish_date)
        val title = binding.findViewById<TextView>(R.id.tv_news_title)
        val desc = binding.findViewById<TextView>(R.id.tv_news_description)
        val img = binding.findViewById<ImageView>(R.id.iv_news_photo)
        val bookmark = binding.findViewById<ImageView>(R.id.iv_bookmark)
        val details = binding.findViewById<TextView>(R.id.tv_see_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val newsItem = newsList[position]

        holder.publishedDate.text = newsItem.publishedAt
        holder.title.text = newsItem.title
        holder.desc.text = newsItem.description
        holder.bookmark.setImageResource(R.drawable.icon_bookmark_added)

        //loading news image with glide
        Glide
            .with(holder.itemView.context)
            .load(newsItem.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.img)



        holder.details.setOnClickListener {
            // creating the instance of the bundle
            val bundle = Bundle()
            val localArticle = LocalArticle(
                newsItem.id,
                newsItem.author,
                newsItem.title,
                newsItem.description,
                newsItem.urlToImage,
                newsItem.publishedAt,
                newsItem.url,
                newsItem.category,
                true
            )
            bundle.putParcelable(URL_KEY, localArticle)
            //navigate to web view fragment
            holder.itemView.findNavController().navigate(R.id.detailsFragment, bundle) //navigate(R.id.detailsFragment,newsItem)
        }
        val localArticle = LocalArticle(
            newsItem.id,
            newsItem.author,
            newsItem.title,
            newsItem.description,
            newsItem.urlToImage,
            newsItem.publishedAt,
            newsItem.url,
            newsItem.category,
            false
        )

        holder.bookmark.setOnClickListener {
            //delete data from bookmark table
            viewModel.deleteBookmarkArticle(newsItem)
            viewModel.updateArticle(localArticle)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}