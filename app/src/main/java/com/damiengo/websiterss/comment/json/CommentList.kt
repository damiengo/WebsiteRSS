package com.damiengo.websiterss.comment.json

import com.damiengo.websiterss.ui.articledetail.model.Model
import com.google.gson.annotations.SerializedName

class CommentList {

    @SerializedName("comments")
    lateinit var comments: List<Comment>

    fun getModels(): MutableList<Model> {
        val models = mutableListOf<Model>()
        if (comments.isNotEmpty()) {
            comments.forEach {
                models.addAll(it.getModel(0))
            }
        }

        return models
    }

}