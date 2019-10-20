package com.damiengo.websiterss.article

import com.damiengo.websiterss.App
import com.damiengo.websiterss.R
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class JsoupArticleReader : ArticleReader {

    override fun read(url: String): Document {
        return Jsoup.connect(url)
            .userAgent(App.getRes().getString(R.string.user_agent))
            .referrer(App.getRes().getString(R.string.referrer))
            .get()
    }

}