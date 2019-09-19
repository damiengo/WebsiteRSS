package com.damiengo.websiterss.article

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import com.prof.rssparser.Article
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MyArticle(_article: Article) {

    private val hhmmFormat = "HH:mm"
    var timeCat: Spanned
    val article: Article

    init {
        setArticleCategoryText(_article)
        setArticleTime(_article)

        _article.description = _article.categories.joinToString(separator = " • ")
        timeCat = HtmlCompat.fromHtml("<b>" + _article.pubDate + "</b> "+_article.description, Html.FROM_HTML_MODE_LEGACY)
        article = _article
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
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val outputStr = outputFormat.format(inputFormat.parse(article.pubDate))
        article.pubDate = outputStr
    }

}
