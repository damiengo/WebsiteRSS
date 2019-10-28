package com.damiengo.websiterss.article

import com.damiengo.websiterss.article.json.Api
import javax.inject.Inject

class JsonProviderStrategy {

    @Inject
    lateinit var service: Api

    fun read(articleId: String) {
        service.getItems(articleId)
    }

}