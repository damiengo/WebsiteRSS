package com.damiengo.websiterss.article

class ArticleDetailProvider(_strategy: ProviderStrategy) {

    val strategy = _strategy

    suspend fun getArticle(url: String) {
        strategy.read(url)
    }

    fun getChapo(): String {
        return strategy.getChapo()
    }

    fun getDescription(): String {
        return strategy.getDescription()
    }

}