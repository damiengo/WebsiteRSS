package com.damiengo.testapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article

class ArticleViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.article_item, parent, false)) {
    private var titleView: TextView? = null
    private var categoryView: TextView? = null

    private var articleTitle: String? = null
    private var articleCategory: String? = null

    init {
        titleView = itemView.findViewById(R.id.article_title)
        categoryView = itemView.findViewById(R.id.article_category)
    }

    fun bind(article: Article, clickListener: (Article) -> Unit) {
        setArticleText(article)
        titleView?.text = articleTitle
        categoryView?.text = articleCategory

        itemView.setOnClickListener { clickListener(article)}
    }

    private fun setArticleText(article: Article) {
        val title = article.title

        val lastIdx = title?.substring(0, 30)!!.lastIndexOf(" - ")

        articleCategory = ""
        articleTitle = article.title

        if(lastIdx != -1) {
            articleCategory = title?.substring(0, lastIdx)
            articleTitle    = title?.substring(lastIdx+3)
        }
    }

}