package com.damiengo.testapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prof.rssparser.Article

class ArticleAdapter(private val dataSource: MutableList<Article>,
                     private val clickListener: (Article) -> Unit) : RecyclerView.Adapter<ArticleViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ArticleViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(context)
        return ArticleViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article: Article = dataSource[position]

        Glide.with(context)
            .load(article.image)
            .centerCrop()
            .into(holder.getImageView())

        holder.bind(article, clickListener)
    }

    override fun getItemCount(): Int = dataSource.size

}