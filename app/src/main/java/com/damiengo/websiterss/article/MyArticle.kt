package com.damiengo.websiterss.article

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import com.prof.rssparser.Article
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MyArticle(_article: Article, _util: ArticleUtil) {

    var timeCat: Spanned
    val article: Article = _article
    val util: ArticleUtil = _util

    init {
        setCategoryText()
        setTime()

        article.description = _article.categories.joinToString(separator = " • ")
        timeCat = HtmlCompat.fromHtml("<b>" + article.pubDate + "</b> "+article.description, Html.FROM_HTML_MODE_LEGACY)
    }

    private fun setCategoryText() {
        val title = article.title
        article.title = util.genTitle(title)
        val categories = util.genCategories(title)

        categories.iterator().forEach {
            article.addCategory(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTime() {
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val outputStr = outputFormat.format(inputFormat.parse(article.pubDate))
        article.pubDate = outputStr
    }

}
