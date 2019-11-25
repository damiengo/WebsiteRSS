package com.damiengo.websiterss.ui.articledetail

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.damiengo.websiterss.R
import com.damiengo.websiterss.article.ArticleDetailProvider
import com.damiengo.websiterss.article.ProviderStrategy
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.util.DaggerDaggerComponent
import com.damiengo.websiterss.util.GlideApp
import kotlinx.android.synthetic.main.article_detail_activity.*
import kotlinx.coroutines.*
import javax.inject.Inject

class ArticleDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var providers: MutableList<ProviderStrategy>

    private lateinit var viewAdapter: ArticleDetailAdapter

    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_activity)

        DaggerDaggerComponent.create().inject(this)

        progress_bar.visibility = View.VISIBLE

        article_content.layoutManager = LinearLayoutManager(this)

        setSupportActionBar(article_toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val title= intent.getStringExtra("title")
        val image= intent.getStringExtra("image")
        var link = intent.getStringExtra("link")

        if(link.isNullOrEmpty()) {
            val uri: Uri = intent.data!!
            link = uri.scheme+"://"+uri.host+uri.path
        }

        collapsing_toolbar.title = title

        GlideApp.with(this)
             .load(image)
             .centerCrop()
             .into(article_image)

        scope.launch(Dispatchers.IO) {
            run breaker@{
                providers.forEach { ps: ProviderStrategy ->
                    val articleDetailProvider = ArticleDetailProvider(ps)
                    val models = articleDetailProvider.getArticle(link)

                    if (models.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            viewAdapter = ArticleDetailAdapter(this@ArticleDetailActivity)

                            models.forEach { model: Model ->
                                viewAdapter.addModel(model)
                            }

                            article_content.adapter = viewAdapter
                            progress_bar.visibility = View.INVISIBLE
                        }
                        return@breaker
                    }
                }
            }
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