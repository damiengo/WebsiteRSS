package com.damiengo.websiterss.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damiengo.websiterss.article.ArticleUtil
import com.damiengo.websiterss.article.MyArticle
import com.damiengo.websiterss.util.DaggerDaggerComponent
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import javax.inject.Inject

class FeedViewModel(var url: String) : ViewModel() {

    @Inject
    lateinit var util: ArticleUtil

    private lateinit var articleListLive: MutableLiveData<MutableList<MyArticle>>

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

    suspend fun fetchFeed() {
        val parser = Parser()
        val myArticleList: MutableList<MyArticle> = mutableListOf()
        val articleList = parser.getArticles(url)
        articleList.forEach { article: Article ->

            val myArticle = MyArticle(article, util)
            myArticleList.add(myArticle)

        }
        setArticleList(myArticleList)
    }

}