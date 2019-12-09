package com.damiengo.websiterss.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.damiengo.websiterss.App
import com.damiengo.websiterss.R
import com.damiengo.websiterss.article.MyArticle
import com.damiengo.websiterss.category.CategoryHolder
import com.damiengo.websiterss.ui.articledetail.ArticleDetailActivity
import com.damiengo.websiterss.util.DaggerDaggerComponent
import com.damiengo.websiterss.util.GlideApp
import com.damiengo.websiterss.util.ThemeUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_header.*
import kotlinx.android.synthetic.main.menu_header.view.*
import kotlinx.coroutines.*
import javax.inject.Inject

const val IMAGE_SIZE = 210

class MainActivity : AppCompatActivity() {

    companion object {
        const val imagesPreload = 25
    }

    private lateinit var currentMenuItem: MenuItem
    private lateinit var viewAdapter: ArticleAdapter
    private lateinit var viewModel: FeedViewModel

    @Inject
    lateinit var categoryHolder: CategoryHolder

    @Inject
    lateinit var networkInformation: NetworkInformation

    @Inject
    lateinit var themeUtil : ThemeUtil

    private val activityJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + activityJob)

    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        themeUtil.applyTheme()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        DaggerDaggerComponent.create().inject(this)

        var currentArticles: MutableList<MyArticle> = mutableListOf()
        var preloadSizeProvider : FixedPreloadSizeProvider<MyArticle>

        nav_view.getHeaderView(0).switch_theme.setOnClickListener {
            switch_theme.setImageResource(R.drawable.ic_sun)
        }

        error.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE

        viewModel = ViewModelProviders.of(this@MainActivity,
                                          viewModelFactory { FeedViewModel(categoryHolder.getDefaultUrl()) }).get(FeedViewModel::class.java)

        list_articles.layoutManager = LinearLayoutManager(this)
        list_articles.setHasFixedSize(true)

        viewModel.getArticleList().observe(this, Observer { articles ->
            if (articles != null) {
                if((! ::viewAdapter.isInitialized)||(currentArticles.size == 0)) {
                    viewAdapter = ArticleAdapter(articles) { myArticle: MyArticle ->
                        articleClicked(
                            myArticle
                        )
                    }
                    viewAdapter.setHasStableIds(true)
                    list_articles.adapter = viewAdapter

                    // glide image preloading
                    preloadSizeProvider = FixedPreloadSizeProvider(IMAGE_SIZE, IMAGE_SIZE)
                    val preloader : RecyclerViewPreloader<MyArticle> = RecyclerViewPreloader(GlideApp.with(this),
                        viewAdapter, preloadSizeProvider, imagesPreload)
                    list_articles.addOnScrollListener(preloader)
                }
                else {
                    val diffResult: DiffUtil.DiffResult =
                        DiffUtil.calculateDiff(MyArticleDiffCallback(currentArticles, articles))
                    diffResult.dispatchUpdatesTo(viewAdapter)
                }

                currentArticles = articles
                progress_bar.visibility = View.GONE
                list_articles.visibility = View.VISIBLE
            }
        })

        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.drawer_open, R.string.drawer_closed
        )
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // Initialize current menu item selected
        nav_view.menu.getItem(0).isChecked = true
        currentMenuItem = nav_view.menu.getItem(0)

        nav_view.setNavigationItemSelectedListener { menuItem ->
            currentMenuItem = menuItem
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawer_layout.closeDrawers()

            list_articles.visibility = View.GONE
            progress_bar.visibility = View.VISIBLE
            title = categoryHolder.getCurrentTitle(currentMenuItem.itemId)
            viewModel.url = categoryHolder.getCurrentUrl(currentMenuItem.itemId)
            currentArticles = mutableListOf()
            fetchFeed()

            true
        }

        swipe_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))

        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = true
            fetchFeed()
            swipe_refresh.isRefreshing = false
        }

        list_articles.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        if( ! networkInformation.isAvailable(this)) {
            showError(getString(R.string.no_network))
        }
        else {
            title = categoryHolder.getCurrentTitle(currentMenuItem.itemId)
            fetchFeed()
        }
    }

    private fun fetchFeed() {
        error.visibility = View.GONE
        scope.launch(Dispatchers.IO) {
            try {
                viewModel.fetchFeed()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError(e.message!!)
                }
            }
        }
    }

    private fun showError(message: String) {
        error.text = message
        error.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    private fun articleClicked(myArticle : MyArticle) {
        val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
        intent.putExtra("title",       myArticle.article.title)
        intent.putExtra("description", myArticle.article.description)
        intent.putExtra("image",       myArticle.article.image)
        intent.putExtra("pubDate",     myArticle.article.pubDate)
        intent.putExtra("link",        myArticle.article.link)
        intent.putExtra("categories",  myArticle.article.categories.joinToString(separator = " • "))
        startActivity(intent)
    }

}