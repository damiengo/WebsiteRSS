package com.damiengo.websiterss.article.json

import com.damiengo.websiterss.ui.articledetail.model.EmptyModel
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.google.gson.annotations.SerializedName

class Format {

    @SerializedName("landscape")
    lateinit var landscape: Landscape

    fun getModel(): Model {
        if(hasLandscape()) {
            return landscape.getModel()
        }
        return EmptyModel()
    }

    private fun hasLandscape(): Boolean {
        return ::landscape.isInitialized
    }

}