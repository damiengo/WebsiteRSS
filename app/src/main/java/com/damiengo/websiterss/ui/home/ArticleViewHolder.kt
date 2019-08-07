package com.damiengo.websiterss.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.damiengo.websiterss.R
import com.damiengo.websiterss.article.MyArticle

class ArticleViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.article_item, parent, false)) {

    private var titleView: TextView   = itemView.findViewById(R.id.article_title)
    private var timeCatView: TextView = itemView.findViewById(R.id.article_time_category)
    private var imageView: ImageView  = itemView.findViewById(R.id.article_detail_image)

    fun bind(myArticle: MyArticle, clickListener: (MyArticle) -> Unit) {
        titleView.text   = myArticle.article.title
        timeCatView.text = myArticle.timeCat

        itemView.setOnClickListener { clickListener(myArticle)}
    }

    fun getImageView(): ImageView {
        return imageView
    }

}