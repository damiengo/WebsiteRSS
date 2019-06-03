package com.damiengo.testapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.prof.rssparser.Article

class ArticleViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.article_item, parent, false)) {
    private var titleView: TextView? = null


    init {
        titleView = itemView.findViewById(R.id.article_title)
    }

    fun bind(article: Article) {
        titleView?.text = article.title
    }

}