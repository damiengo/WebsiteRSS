package com.damiengo.testapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.article_detail_activity.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.util.logging.Logger

class ArticleDetailActivity : AppCompatActivity() {

    val log = Logger.getLogger(ArticleDetailActivity::class.java.name)

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_activity)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val image = intent.getStringExtra("image")
        val pubDate = intent.getStringExtra("pubDate")
        val link = intent.getStringExtra("link")

        article_title.text = title
        article_date.text = pubDate

        Glide.with(this)
             .load(image)
             .into(article_image)

        coroutineScope.launch(Dispatchers.Main) {
            val document = withContext(Dispatchers.IO) {
                Jsoup.connect(link).get()
            }
            val chapo = document.select(".Article__chapo").text()
            val builder = StringBuilder()
            article_chapo.text = chapo

            document.select(".article__body .Paragraph").forEach { ele ->
                builder.append(ele.text()).append("\n")
            }

            article_description.text = builder.toString()
        }

    }

}