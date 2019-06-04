package com.damiengo.testapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.util.logging.Logger

class ArticleDetailActivity : AppCompatActivity() {

    val log = Logger.getLogger(ArticleDetailActivity::class.java.name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_activity)
    }

}