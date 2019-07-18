package com.damiengo.websiterss.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.damiengo.websiterss.R
import com.prof.rssparser.Article

class ArticleViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.article_item, parent, false)) {

    private var titleView: TextView     = itemView.findViewById(R.id.article_title)
    private var timeCatView: TextView   = itemView.findViewById(R.id.article_time_category)
    private var imageView: ImageView    = itemView.findViewById(R.id.article_detail_image)

    fun bind(article: Article, clickListener: (Article) -> Unit) {
        val timeCat: String = article.pubDate + " " + article.description
        titleView.text   = article.title
        timeCatView.text = timeCat

        itemView.setOnClickListener { clickListener(article)}
    }

    fun getImageView(): ImageView {
        return imageView
    }

}