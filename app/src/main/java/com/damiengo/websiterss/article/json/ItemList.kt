package com.damiengo.websiterss.article.json

import com.damiengo.websiterss.ui.articledetail.model.Model
import com.google.gson.annotations.SerializedName

class ItemList {

    @SerializedName("items")
    lateinit var items: List<Item>

    fun getModels(): MutableList<Model> {
        var models = mutableListOf<Model>()
        if (items.isNotEmpty()) {
            items.forEach {
                models.addAll(it.getModels())
            }
        }

        return models
    }

}