package com.damiengo.testapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prof.rssparser.Article
import kotlinx.android.synthetic.main.article_detail_activity.*
import kotlinx.coroutines.withContext

class ArticleViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.article_item, parent, false)) {
    private var titleView: TextView? = null
    private var categoryView: TextView? = null
    private var imageView: ImageView

    private var articleTitle: String? = null
    private var articleCategory: String? = null

    init {
        titleView = itemView.findViewById(R.id.article_title)
        categoryView = itemView.findViewById(R.id.article_category)
        imageView = itemView.findViewById(R.id.article_detail_image)
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

    fun getImageView(): ImageView {
        return imageView
    }

}