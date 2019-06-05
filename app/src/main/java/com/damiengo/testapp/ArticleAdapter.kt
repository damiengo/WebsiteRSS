package com.damiengo.testapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.prof.rssparser.Article

class ArticleAdapter(private val dataSource: MutableList<Article>,
                     private val clickListener: (Article) -> Unit) : RecyclerView.Adapter<ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ArticleViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article: Article = dataSource[position]
        holder.bind(article, clickListener)
    }

    override fun getItemCount(): Int = dataSource.size

}