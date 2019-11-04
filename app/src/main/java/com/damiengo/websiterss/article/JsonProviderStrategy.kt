package com.damiengo.websiterss.article

import com.damiengo.websiterss.article.json.Api
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.util.DaggerDaggerComponent
import javax.inject.Inject

class JsonProviderStrategy : ProviderStrategy {

    @Inject
    lateinit var service: Api

    @Inject
    lateinit var util: ArticleUtil

    init {
        DaggerDaggerComponent.create().inject(this)
    }

    override suspend fun read(url: String): MutableList<Model> {
        val articleId = util.getArticleIdFromUrl(url)

        val response = service.getItems(articleId)

        if(response.isSuccessful) {
            return response.body()!!.getModels()
        }

        return mutableListOf<Model>()
    }

}