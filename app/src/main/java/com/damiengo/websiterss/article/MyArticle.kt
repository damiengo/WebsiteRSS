package com.damiengo.websiterss.article

import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.prof.rssparser.Article

class MyArticle(_article: Article, _util: ArticleUtil) {

    var timeCat: Spanned
    val article: Article = _article
    val util: ArticleUtil = _util

    init {
        setCategoryText()

        article.pubDate = util.genPubDate(article.pubDate)
        article.description = article.categories.joinToString(separator = " • ")
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

}
