package com.damiengo.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.prof.rssparser.Parser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    val log = Logger.getLogger(MainActivity::class.java.name)
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)

        coroutineScope.launch(Dispatchers.Main) {
            try {
                val parser = Parser()
                val articleList = parser.getArticles("https://www.lequipe.fr/rss/actu_rss.xml")

                log.info(articleList[0].title)

                viewAdapter = ArticleAdapter(articleList)

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



}
