package com.damiengo.websiterss.article

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import com.prof.rssparser.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        var rssFormatter = DateTimeFormatter.RFC_1123_DATE_TIME
        val date = LocalDateTime.parse(article.pubDate, rssFormatter)
        var formatter = DateTimeFormatter.ofPattern(hhmmFormat)
        article.pubDate = date.format(formatter)
    }

}
