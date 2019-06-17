package com.damiengo.testapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.logging.Logger
import androidx.appcompat.app.ActionBarDrawerToggle


class MainActivity : AppCompatActivity() {
    private val log = Logger.getLogger(MainActivity::class.java.name)
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val parser = Parser()

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var articleList: MutableList<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rssActu = "https://www.lequipe.fr/rss/actu_rss.xml"
        val rssFoot = "http://www.lequipe.fr/rss/actu_rss_Football.xml"
        val rssFootTransferts = "http://www.lequipe.fr/rss/actu_rss_Transferts.xml"
        val rssTennis = "http://www.lequipe.fr/rss/actu_rss_Tennis.xml"
        val rssRugby = "http://www.lequipe.fr/rss/actu_rss_Rugby.xml"
        val rssBasket = "http://www.lequipe.fr/rss/actu_rss_Basket.xml"

        viewManager = LinearLayoutManager(this)

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

        nav_view.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawer_layout.closeDrawers()

            // Handle navigation view item clicks here.
            when (menuItem.itemId) {
                R.id.nav_actu -> {
                    setTitle("Actualité")
                    getArticles(rssActu)
                }
                R.id.nav_foot -> {
                    setTitle("Football")
                    getArticles(rssFoot)
                }
                R.id.nav_transferts -> {
                    setTitle("Transferts")
                    getArticles(rssFootTransferts)
                }
                R.id.nav_basket -> {
                    setTitle("Basket")
                    getArticles(rssBasket)
                }
                R.id.nav_tennis -> {
                    setTitle("Tennis")
                    getArticles(rssTennis)
                }
                R.id.nav_rugby -> {
                    setTitle("Rugby")
                    getArticles(rssRugby)
                }
            }

            true
        }

        swipe_refresh.setOnRefreshListener {
            getArticles(rssActu)
        }

        coroutineScope.launch(Dispatchers.Main) {
            try {
                articleList = parser.getArticles(rssActu)
                viewAdapter = ArticleAdapter(articleList) { article : Article -> articleClicked(article) }
                setTitle("Actualité")

                list_articles.apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)

                    // use a linear layout manager
                    layoutManager = viewManager

                    // specify an viewAdapter (see also next example)
                    adapter = viewAdapter
                }
            } catch (e: Exception) {
                // Handle the exception
                log.severe("Error reading feed: "+e.message)
            }
        }
    }

    private fun getArticles(rssFeed: String) {
        articleList.clear()
        coroutineScope.launch(Dispatchers.Main) {
            try {
                val newArticleList = parser.getArticles(rssFeed)
                articleList.addAll(newArticleList)
                viewAdapter.notifyDataSetChanged()
                list_articles.smoothScrollToPosition(0)
                swipe_refresh.isRefreshing = false
            } catch (e: Exception) {
                // Handle the exception
                log.severe("Error reading feed: "+e.message)
            }
        }
    }

    private fun articleClicked(article : Article) {
        val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
        intent.putExtra("title", article.title)
        intent.putExtra("description", article.description)
        intent.putExtra("image", article.image)
        intent.putExtra("pubDate", article.pubDate)
        intent.putExtra("link", article.link)
        startActivity(intent)
    }

}
