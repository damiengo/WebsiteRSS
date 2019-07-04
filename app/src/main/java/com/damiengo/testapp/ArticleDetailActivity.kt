package com.damiengo.testapp

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.article_detail_activity.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.util.logging.Logger
import android.view.MenuItem

class ArticleDetailActivity : AppCompatActivity() {

    val log = Logger.getLogger(ArticleDetailActivity::class.java.name)

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_activity)

        setSupportActionBar(article_toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val title      = intent.getStringExtra("title")
        val image      = intent.getStringExtra("image")
        val pubDate    = intent.getStringExtra("pubDate")
        val link       = intent.getStringExtra("link")
        val categories = intent.getStringExtra("categories")

        article_date.text        = pubDate
        article_categories.text  = categories
        collapsing_toolbar.title = title

        GlideApp.with(this)
             .load(image)
             .centerCrop()
             .into(article_image)

        coroutineScope.launch(Dispatchers.Main) {
            val document = withContext(Dispatchers.IO) {
                Jsoup.connect(link).get()
            }
            val chapo = document.select(".Article__chapo").text()
            val builder = StringBuilder()
            article_chapo.text = chapo

            document.select(".article__body .Paragraph").forEach { ele ->
                builder.append(ele.text()).append("\n\n")
            }

            article_description.text = builder.toString()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Back button
        if (android.R.id.home == item.itemId) {
            onBackPressed()
        }
        return true
    }

}