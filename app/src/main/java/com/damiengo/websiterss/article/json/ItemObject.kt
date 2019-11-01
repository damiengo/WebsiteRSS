package com.damiengo.websiterss.article.json

import com.damiengo.websiterss.ui.articledetail.model.Model
import com.google.gson.annotations.SerializedName

class ItemObject {

    @SerializedName("paragraphs")
    lateinit var paragraphs: List<Paragraph>

    fun getModels(): List<Model> {
        var models = mutableListOf<Model>()

        if (hasParagraphs()) {
            paragraphs.forEach {
                models.add(it.getModel())
            }
        }

        return models
    }

    private fun hasParagraphs(): Boolean {
        return ::paragraphs.isInitialized && paragraphs.isNotEmpty()
    }

}