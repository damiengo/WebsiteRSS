package com.damiengo.websiterss.ui.articledetail

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.damiengo.websiterss.article.ArticleDetailProvider
import com.damiengo.websiterss.article.ProviderStrategy
import com.damiengo.websiterss.comment.JsonProvider
import com.damiengo.websiterss.databinding.ArticleDetailActivityBinding
import com.damiengo.websiterss.di.DaggerComponent
import com.damiengo.websiterss.di.DaggerDaggerComponent
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.ui.articledetail.model.SiteLinkModel
import com.damiengo.websiterss.util.GlideApp
import com.damiengo.websiterss.util.ThemeUtil
import kotlinx.coroutines.*
import javax.inject.Inject

class ArticleDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var providers: MutableList<ProviderStrategy>

    @Inject
    lateinit var commentProvider : JsonProvider

    //@Inject
    lateinit var themeUtil: ThemeUtil

    private lateinit var viewAdapter: ArticleDetailAdapter

    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var binding: ArticleDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        themeUtil = ThemeUtil(this)
        themeUtil.applyTheme()
        super.onCreate(savedInstanceState)
        binding = ArticleDetailActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        DaggerDaggerComponent.create().inject(this)

        binding.progressBar.visibility = View.VISIBLE

        binding.articleContent.layoutManager = LinearLayoutManager(this)

        setSupportActionBar(binding.articleToolbar)
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

        binding.collapsingToolbar.title = title

        GlideApp.with(this)
             .load(image)
             .centerCrop()
             .into(binding.articleImage)

        scope.launch(Dispatchers.IO) {
            run breaker@{
                providers.forEach { ps: ProviderStrategy ->
                    val articleDetailProvider = ArticleDetailProvider(ps)
                    val models = articleDetailProvider.getArticle(link)

                    if (models.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            viewAdapter = ArticleDetailAdapter(this@ArticleDetailActivity, binding.articleImage, binding.collapsingToolbar)

                            models.forEach { model: Model ->
                                viewAdapter.addModel(model)
                            }

                            binding.articleContent.adapter = viewAdapter
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        viewAdapter.addModel(SiteLinkModel(link))
                        val commentModels = commentProvider.read(link)
                        withContext(Dispatchers.Main) {
                            commentModels.forEach { model: Model ->
                                viewAdapter.addModel(model)
                            }
                            viewAdapter.notifyDataSetChanged()
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