package com.damiengo.websiterss.article.json

import com.damiengo.websiterss.ui.articledetail.model.EmptyModel
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.google.gson.annotations.SerializedName

class Media {

    @SerializedName("formats")
    lateinit var format: Format

    fun getModel(): Model {
        if(hasFormat()) {
            return format.getModel()
        }
        return EmptyModel()
    }

    private fun hasFormat(): Boolean {
        return ::format.isInitialized
    }

}