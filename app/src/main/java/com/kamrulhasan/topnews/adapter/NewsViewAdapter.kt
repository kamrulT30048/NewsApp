package com.kamrulhasan.topnews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.model.Article
import com.kamrulhasan.topnews.utils.DateConverter

class NewsViewAdapter(
    private val context: Context,
    private val newsList: List<Article>
): RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder>() {

    class NewsViewHolder(binding: View) : RecyclerView.ViewHolder(binding.rootView){

        val publishedDate = binding.findViewById<TextView>(R.id.tv_publish_date)
        val title = binding.findViewById<TextView>(R.id.tv_news_title)
        val desc = binding.findViewById<TextView>(R.id.tv_news_description)
        val img = binding.findViewById<ImageView>(R.id.iv_news_photo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val newsItem = newsList[position]
//        val dateLong = DateConverter.convertStringToLong(newsItem.publishedAt.toString())
        holder.publishedDate.text = newsItem.publishedAt

        holder.title.text = newsItem.title
        holder.desc.text = newsItem.description

        Glide
            .with(holder.itemView.context)
            .load(newsItem.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.img)

    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}