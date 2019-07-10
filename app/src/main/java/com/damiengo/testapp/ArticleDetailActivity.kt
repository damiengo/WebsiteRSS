package com.damiengo.testapp

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.article_detail_activity.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.util.logging.Logger
import android.view.MenuItem
import android.view.View
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.article_detail_activity.progress_bar

class ArticleDetailActivity : AppCompatActivity() {

    val log = Logger.getLogger(ArticleDetailActivity::class.java.name)

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_activity)

        progress_bar.visibility = View.VISIBLE

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
        article_title.text       = title
        collapsing_toolbar.title = title

        GlideApp.with(this)
             .load(image)
             .centerCrop()
             .into(article_image)

        coroutineScope.launch(Dispatchers.Main) {
            val document = withContext(Dispatchers.IO) {
                Jsoup.connect(link)
                    .userAgent("Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
                    .referrer("http://www.google.com")
                    .get()
            }
            val chapo = document.select(".Article__chapo").text()
            log.info(document.html())
            val builder = StringBuilder()
            article_chapo.text = chapo

            document.select(".article__body .Paragraph").forEach { ele ->
                builder.append(ele.html()).append("<br /><br />")
            }
            var htmlDesc = builder.toString()
            // Delete paragraph
            htmlDesc = htmlDesc.replace("<p[^>]*>".toRegex(), "")
            htmlDesc = htmlDesc.replace("</p>", "")
            // Delete links
            htmlDesc = htmlDesc.replace("<a[^>]*>".toRegex(), "")
            htmlDesc = htmlDesc.replace("</a>", "")
            // Replace title
            htmlDesc = htmlDesc.replace("<h3[^>]*>".toRegex(), "<p><b>")
            htmlDesc = htmlDesc.replace("</h3>", "</b></p>")
            article_description.text = HtmlCompat.fromHtml(htmlDesc, Html.FROM_HTML_MODE_LEGACY)

            progress_bar.visibility = View.INVISIBLE
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