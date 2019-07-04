package com.damiengo.testapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article

class ArticleViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.article_item, parent, false)) {
    private var titleView: TextView? = null
    private var categoryView: TextView? = null
    private var timeView: TextView? = null
    private var imageView: ImageView


    init {
        titleView    = itemView.findViewById(R.id.article_title)
        categoryView = itemView.findViewById(R.id.article_category)
        timeView     = itemView.findViewById(R.id.article_time)
        imageView    = itemView.findViewById(R.id.article_detail_image)
    }

    fun bind(article: Article, clickListener: (Article) -> Unit) {
        titleView?.text    = article.title
        categoryView?.text = article.categories.joinToString(separator = " • ")
        timeView?.text     = article.pubDate

        itemView.setOnClickListener { clickListener(article)}
    }

    fun getImageView(): ImageView {
        return imageView
    }

}