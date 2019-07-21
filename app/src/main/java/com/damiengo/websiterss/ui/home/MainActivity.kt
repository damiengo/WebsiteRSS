package com.damiengo.websiterss.ui.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DividerItemDecoration
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.damiengo.websiterss.ui.articledetail.ArticleDetailActivity
import com.damiengo.websiterss.R
import com.damiengo.websiterss.article.MyArticle
import com.damiengo.websiterss.util.GlideApp

class MainActivity : AppCompatActivity() {
    private val log = Logger.getLogger(MainActivity::class.java.name)

    private val rssActu           = "https://www.lequipe.fr/rss/actu_rss.xml"
    private val rssFoot           = "http://www.lequipe.fr/rss/actu_rss_Football.xml"
    private val rssFootTransferts = "http://www.lequipe.fr/rss/actu_rss_Transferts.xml"
    private val rssTennis         = "http://www.lequipe.fr/rss/actu_rss_Tennis.xml"
    private val rssRugby          = "http://www.lequipe.fr/rss/actu_rss_Rugby.xml"
    private val rssBasket         = "http://www.lequipe.fr/rss/actu_rss_Basket.xml"
    private val rssCyclisme       = "http://www.lequipe.fr/rss/actu_rss_Cyclisme.xml"

    private val imageSize: Int = 210
    private val imagesPreload: Int = 25

    private lateinit var viewAdapter: ArticleAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel:   FeedViewModel

    private lateinit var currentMenuItem: MenuItem

    private lateinit var preloadSizeProvider : FixedPreloadSizeProvider<MyArticle>

    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        network_state.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE

        viewManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this@MainActivity,
                                          viewModelFactory { FeedViewModel(rssActu) }).get(FeedViewModel::class.java)

        viewAdapter = ArticleAdapter(ArrayList()) { myArticle: MyArticle ->
            articleClicked(
                myArticle
            )
        }
        viewAdapter.setHasStableIds(true)

        list_articles.layoutManager = LinearLayoutManager(this)
        list_articles.itemAnimator = DefaultItemAnimator()
        list_articles.setHasFixedSize(true)
        list_articles.adapter = viewAdapter

        viewModel.getArticleList().observe(this, Observer { articles ->
            if (articles != null) {
                viewAdapter = ArticleAdapter(articles) { myArticle: MyArticle ->
                    articleClicked(
                        myArticle
                    )
                }
                viewAdapter.setHasStableIds(true)

                // glide image preloading
                preloadSizeProvider = FixedPreloadSizeProvider(imageSize, imageSize)
                val preloader : RecyclerViewPreloader<MyArticle> = RecyclerViewPreloader(GlideApp.with(this),
                    viewAdapter, preloadSizeProvider, imagesPreload)
                list_articles.addOnScrollListener(preloader)

                list_articles.adapter = viewAdapter
                progress_bar.visibility = View.GONE
                list_articles.visibility = View.VISIBLE
                swipe_refresh.isRefreshing = false
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
            setTitleFromCategory()
            viewModel.url = getUrlFromCategory()
            viewModel.fetchFeed()

            true
        }

        swipe_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))

        swipe_refresh.setOnRefreshListener {
            viewAdapter.notifyDataSetChanged()
            swipe_refresh.isRefreshing = true
            viewModel.fetchFeed()
        }

        list_articles.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        if( ! isNetworkAvailable(this)) {
            network_state.text = getString(R.string.no_network)
            progress_bar.visibility = View.GONE
            network_state.visibility = View.VISIBLE
        }
        else {
            setTitleFromCategory()
            viewModel.fetchFeed()
        }
    }

    private fun setTitleFromCategory() {
        when (currentMenuItem.itemId) {
            R.id.nav_actu -> {
                setTitle("Actualité")
            }
            R.id.nav_foot -> {
                setTitle("Football")
            }
            R.id.nav_transferts -> {
                setTitle("Transferts")
            }
            R.id.nav_basket -> {
                setTitle("Basket")
            }
            R.id.nav_tennis -> {
                setTitle("Tennis")
            }
            R.id.nav_rugby -> {
                setTitle("Rugby")
            }
            R.id.nav_cyclisme -> {
                setTitle("Cyclisme")
            }
        }
    }

    private fun getUrlFromCategory(): String {
        when (currentMenuItem.itemId) {
            R.id.nav_actu -> {
                return rssActu
            }
            R.id.nav_foot -> {
                return rssFoot
            }
            R.id.nav_transferts -> {
                return rssFootTransferts
            }
            R.id.nav_basket -> {
                return rssBasket
            }
            R.id.nav_tennis -> {
                return rssTennis
            }
            R.id.nav_rugby -> {
                return rssRugby
            }
            R.id.nav_cyclisme -> {
                return rssCyclisme
            }
        }

        return rssActu
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
