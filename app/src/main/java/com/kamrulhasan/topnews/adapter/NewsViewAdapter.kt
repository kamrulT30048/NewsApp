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
import com.kamrulhasan.topnews.utils.URL_KEY
import com.kamrulhasan.topnews.viewmodel.TopNewsViewModel

private const val TAG = "NewsViewAdapter"

class NewsViewAdapter(
    private val context: Context,
    private val newsList: List<LocalArticle>,
    private val viewModel: TopNewsViewModel
): RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder>() {

//    val viewModel = ViewModelProvider()[TopNewsViewModel::class.java]

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
//        val viewModel = ViewModelProvider(holder.itemView.context)[TopNewsViewModel::class.java]

        holder.publishedDate.text = newsItem.publishedAt
        holder.title.text = newsItem.title
        holder.desc.text = newsItem.description

        //loading news image with glide
        Glide
            .with(holder.itemView.context)
            .load(newsItem.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.img)

        if(newsItem.bookmark){
            holder.bookmark.setImageResource(R.drawable.icon_bookmark_added)
        }else{
            holder.bookmark.setImageResource(R.drawable.icon_bookmark)
        }

        holder.details.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(URL_KEY, newsItem)
//            Toast.makeText(holder.itemView.context, "id ${newsItem.id}", Toast.LENGTH_SHORT).show()
            //navigate to web view fragment
            holder.itemView.findNavController().navigate(R.id.detailsFragment, bundle) //navigate(R.id.detailsFragment,newsItem)
        }

        holder.bookmark.setOnClickListener {

            if(newsItem.bookmark){
                val localArticle = LocalArticle(
                    newsItem.id,
                    newsItem.author,
                    newsItem.title,
                    newsItem.description,
                    newsItem.urlToImage,
                    newsItem.publishedAt,
                    newsItem.url,
                    newsItem.category,
                    false )
                viewModel.updateArticle(localArticle)
//                holder.bookmark.setImageResource(R.drawable.icon_bookmark)
            }else{
                val localArticle = LocalArticle(
                    newsItem.id,
                    newsItem.author,
                    newsItem.title,
                    newsItem.description,
                    newsItem.urlToImage,
                    newsItem.publishedAt,
                    newsItem.url,
                    newsItem.category,
                    true )
                viewModel.updateArticle(localArticle)
//                holder.bookmark.setImageResource(R.drawable.icon_bookmark_added)

                // add bookmark table
                val bookmarkArticle = BookMarkArticle(
                    newsItem.id,
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