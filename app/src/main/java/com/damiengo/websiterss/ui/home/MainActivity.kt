package com.damiengo.websiterss.ui.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.damiengo.websiterss.ui.articledetail.ArticleDetailActivity
import com.damiengo.websiterss.R
import com.damiengo.websiterss.article.MyArticle
import com.damiengo.websiterss.category.CategoriesBuilder
import com.damiengo.websiterss.category.Category
import com.damiengo.websiterss.util.DaggerMagicBox
import com.damiengo.websiterss.util.GlideApp
import javax.inject.Inject

const val IMAGE_SIZE = 210

class MainActivity : AppCompatActivity() {

    companion object{

        const val imagesPreload = 25

    }

    private lateinit var currentMenuItem: MenuItem
    private lateinit var viewAdapter: ArticleAdapter

    @Inject
    lateinit var categoriesBuilder: CategoriesBuilder

    lateinit var categories: Map<Int, Category>

    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMagicBox.create().inject(this)
        categories = categoriesBuilder.build()

        var currentArticles: MutableList<MyArticle> = mutableListOf()
        var preloadSizeProvider : FixedPreloadSizeProvider<MyArticle>

        network_state.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE

        var defaultUrl: String = ""
        categories[R.id.nav_actu]?.let {
            defaultUrl = it.url
        }

        var viewModel = ViewModelProviders.of(this@MainActivity,
                                          viewModelFactory { FeedViewModel(defaultUrl) }).get(FeedViewModel::class.java)

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
            title = getTitleFromCategory()
            viewModel.url = getUrlFromCategory()
            currentArticles = mutableListOf()
            viewModel.fetchFeed()

            true
        }

        swipe_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))

        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = true
            viewModel.fetchFeed()
            swipe_refresh.isRefreshing = false
        }

        list_articles.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        if( ! isNetworkAvailable(this)) {
            network_state.text = getString(R.string.no_network)
            progress_bar.visibility = View.GONE
            network_state.visibility = View.VISIBLE
        }
        else {
            title = getTitleFromCategory()
            viewModel.fetchFeed()
        }
    }

    private fun getTitleFromCategory(): String {
        categories[currentMenuItem.itemId]?.let {
            return it.title
        }

        return ""
    }

    private fun getUrlFromCategory(): String {
        categories[currentMenuItem.itemId]?.let {
            return it.url
        }

        categories[R.id.nav_actu]?.let {
            return it.url
        }

        return ""
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

    private fun isNetworkAvailable(context : Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

}
