package com.damiengo.websiterss.article.json

import com.damiengo.websiterss.ui.articledetail.model.Model
import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("objet")
    lateinit var objet: ItemObject

    fun getModels(): List<Model> {
        return objet.getModels()
    }

}