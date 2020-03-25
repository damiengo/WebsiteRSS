package com.damiengo.websiterss.article.json

import com.damiengo.websiterss.di.DaggerDaggerComponent
import com.damiengo.websiterss.ui.articledetail.model.EmptyModel
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.ui.articledetail.model.ModelFactory
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class Landscape {

    @Inject
    lateinit var modelFactory: ModelFactory

    @SerializedName("url")
    lateinit var url: String

    init {
        DaggerDaggerComponent.create().inject(this)
    }

    fun getModel(): Model {
        if(hasUrl()) {
            return modelFactory.buildTitleImageModel(url)
        }
        return EmptyModel()
    }

    private fun hasUrl(): Boolean {
        return ::url.isInitialized
    }

}