package com.damiengo.websiterss.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damiengo.websiterss.article.*
import com.damiengo.websiterss.util.DaggerDaggerComponent
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject

class FeedViewModel(var url: String) : ViewModel() {

    @Inject
    lateinit var util: ArticleUtil

    private val log = Logger.getLogger(FeedViewModel::class.java.name)

    private lateinit var articleListLive: MutableLiveData<MutableList<MyArticle>>

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        DaggerDaggerComponent.create().inject(this)
    }


    fun getArticleList(): MutableLiveData<MutableList<MyArticle>> {
        if (!::articleListLive.isInitialized) {
            articleListLive = MutableLiveData()
        }
        return articleListLive
    }

    private fun setArticleList(articleList: MutableList<MyArticle>) {
        articleListLive.postValue(articleList)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchFeed() {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val parser = Parser()
                val myArticleList: MutableList<MyArticle> = mutableListOf()
                val articleList = parser.getArticles(url)
                articleList.forEach { article: Article ->

                    val myArticle = MyArticle(article, util)
                    myArticleList.add(myArticle)

                }
                setArticleList(myArticleList)
            } catch (e: Exception) {
                e.printStackTrace()
                setArticleList(mutableListOf())
            }
        }
    }

}