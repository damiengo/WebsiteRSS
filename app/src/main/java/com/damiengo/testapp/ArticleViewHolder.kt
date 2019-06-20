package com.damiengo.testapp

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prof.rssparser.Article
import kotlinx.android.synthetic.main.article_detail_activity.*
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ArticleViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.article_item, parent, false)) {
    private var titleView: TextView? = null
    private var categoryView: TextView? = null
    private var timeView: TextView? = null
    private var imageView: ImageView

    private var articleTitle: String? = null
    private var articleCategory: String? = null
    private var articleTime: String? = null

    init {
        titleView = itemView.findViewById(R.id.article_title)
        categoryView = itemView.findViewById(R.id.article_category)
        timeView = itemView.findViewById(R.id.article_time)
        imageView = itemView.findViewById(R.id.article_detail_image)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(article: Article, clickListener: (Article) -> Unit) {
        setArticleText(article)
        setArticleTime(article)

        titleView?.text = articleTitle
        categoryView?.text = articleCategory
        timeView?.text = articleTime

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setArticleTime(article: Article) {
        var rssFormatter = DateTimeFormatter.RFC_1123_DATE_TIME
        val date = LocalDateTime.parse(article.pubDate, rssFormatter)
        var formatter = DateTimeFormatter.ofPattern("HH:mm")
        articleTime = date.format(formatter)
    }

    fun getImageView(): ImageView {
        return imageView
    }

}