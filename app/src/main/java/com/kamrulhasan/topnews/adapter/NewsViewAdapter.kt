package com.kamrulhasan.topnews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kamrulhasan.topnews.R
import com.kamrulhasan.topnews.databinding.ItemNewsBinding
import com.kamrulhasan.topnews.model.Article

class NewsViewAdapter(
    private val context: Context,
    private val newsList: List<Article>
): RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder>() {

    class NewsViewHolder(binding: View) : RecyclerView.ViewHolder(binding.rootView){
        val title = binding.findViewById<TextView>(R.id.tv_news_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.title.text = newsItem.title
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}