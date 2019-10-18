package com.damiengo.websiterss.ui.articledetail

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
import com.damiengo.websiterss.R
import com.damiengo.websiterss.article.ArticleUtil
import com.damiengo.websiterss.util.DaggerDaggerComponent
import com.damiengo.websiterss.util.GlideApp
import kotlinx.android.synthetic.main.article_detail_activity.progress_bar
import javax.inject.Inject

class ArticleDetailActivity : AppCompatActivity() {

    val log = Logger.getLogger(ArticleDetailActivity::class.java.name)

    @Inject
    lateinit var util: ArticleUtil

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_activity)

        DaggerDaggerComponent.create().inject(this)

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
                    .userAgent(resources.getString(R.string.user_agent))
                    .referrer(resources.getString(R.string.referrer))
                    .get()
            }

            article_chapo.text = util.genChapoFromDom(document)
            article_description.text = HtmlCompat.fromHtml(util.genDescriptionFromDom(document), Html.FROM_HTML_MODE_LEGACY)

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