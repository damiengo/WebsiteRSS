package com.damiengo.websiterss.article

import org.jsoup.nodes.Document

interface ArticleReader {

    fun read(url: String): Document

}