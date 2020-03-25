package com.damiengo.websiterss.comment

import com.damiengo.websiterss.article.ArticleUtil
import com.damiengo.websiterss.api.EquipeApi
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.util.DaggerDaggerComponent
import javax.inject.Inject

class JsonProvider {

    @Inject
    lateinit var service: EquipeApi

    @Inject
    lateinit var util: ArticleUtil

    init {
        DaggerDaggerComponent.create().inject(this)
    }

    suspend fun read(url: String): MutableList<Model> {
        val articleId = util.getArticleIdFromUrl(url)
        val articleCategorySlug = util.getArticleCategorySlugFromUrl(url)

        val response = service.getComments(articleCategorySlug, articleId, 100, 0)

        if(response.isSuccessful) {
            return response.body()!!.getModels()
        }

        return mutableListOf()
    }

}