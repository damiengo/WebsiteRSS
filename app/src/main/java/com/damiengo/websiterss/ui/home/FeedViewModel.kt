package com.damiengo.websiterss.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.logging.Logger

class FeedViewModel(var url: String) : ViewModel() {

    private val log = Logger.getLogger(FeedViewModel::class.java.name)

    private lateinit var articleListLive: MutableLiveData<MutableList<Article>>

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getArticleList(): MutableLiveData<MutableList<Article>> {
        if (!::articleListLive.isInitialized) {
            articleListLive = MutableLiveData()
        }
        return articleListLive
    }

    private fun setArticleList(articleList: MutableList<Article>) {
        articleListLive.postValue(articleList)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun fetchFeed() {
        coroutineScope.launch(Dispatchers.Main) {
            try {
                val parser = Parser()
                val articleList = parser.getArticles(url)
                setArticleList(articleList)
            } catch (e: Exception) {
                e.printStackTrace()
                setArticleList(mutableListOf())
            }
        }
    }

}