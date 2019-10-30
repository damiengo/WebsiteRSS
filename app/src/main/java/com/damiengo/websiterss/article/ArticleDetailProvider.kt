package com.damiengo.websiterss.article

import com.damiengo.websiterss.ui.articledetail.model.Model

class ArticleDetailProvider(_strategy: ProviderStrategy) {

    private val strategy = _strategy

    suspend fun getArticle(url: String): MutableList<Model> {
        return strategy.read(url)
    }

}