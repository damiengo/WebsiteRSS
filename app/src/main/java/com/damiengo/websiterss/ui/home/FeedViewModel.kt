package com.damiengo.websiterss.ui.home

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.logging.Logger
import com.prof.rssparser.OnTaskCompleted
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class FeedViewModel(var url: String) : ViewModel() {

    private val log = Logger.getLogger(FeedViewModel::class.java.name)

    private val hhmmFormat = "HH:mm"

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchFeed() {
        coroutineScope.launch(Dispatchers.Main) {
            try {
                val parser = Parser()
                val articleList = parser.getArticles(url)
                articleList.forEach { article: Article ->

                    if( ! this@FeedViewModel.articleSetted(article)) {
                        this@FeedViewModel.setArticleCategoryText(article)
                        this@FeedViewModel.setArticleTime(article)

                        article.pubDate =
                            HtmlCompat.fromHtml("<b>" + article.pubDate + "</b> ", Html.FROM_HTML_MODE_LEGACY).toString()
                        article.description = article.categories.joinToString(separator = " • ")
                    }


                }
                setArticleList(articleList)
            } catch (e: Exception) {
                e.printStackTrace()
                setArticleList(mutableListOf())
            }
        }
    }

    private fun setArticleCategoryText(article: Article) {
        val title = article.title

        val lastIdx = title?.substring(0, 30)!!.lastIndexOf(" - ")

        if(lastIdx != -1) {
            // Setting title
            article.title = title.substring(lastIdx+3)
            // Setting categories
            title.substring(0, lastIdx).split(" - ").iterator().forEach {
                article.addCategory(it)
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setArticleTime(article: Article) {
        var rssFormatter = DateTimeFormatter.RFC_1123_DATE_TIME
        val date = LocalDateTime.parse(article.pubDate, rssFormatter)
        var formatter = DateTimeFormatter.ofPattern(hhmmFormat)
        article.pubDate = date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun articleSetted(article:Article): Boolean {
        var formatter = DateTimeFormatter.ofPattern(hhmmFormat)
        var ok:Boolean = false
        try {
            formatter.parse(article.pubDate)
            ok = true
        }
        catch (e:Exception) {}

        return ok
    }

}