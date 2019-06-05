package com.damiengo.testapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.article_detail_activity.*
import java.util.logging.Logger

class ArticleDetailActivity : AppCompatActivity() {

    val log = Logger.getLogger(ArticleDetailActivity::class.java.name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_activity)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val image = intent.getStringExtra("image")
        val pubDate = intent.getStringExtra("pubDate")

        article_title.text = title
        article_description.text = description
        article_date.text = pubDate

        Glide.with(this)
             .load(image)
             .into(article_image)
    }

}