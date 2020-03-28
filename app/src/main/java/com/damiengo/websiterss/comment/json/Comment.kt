package com.damiengo.websiterss.comment.json

import com.damiengo.websiterss.di.DaggerDaggerComponent
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.ui.articledetail.model.ModelFactory
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.inject.Inject

class Comment {

    @Inject
    lateinit var modelFactory: ModelFactory

    @SerializedName("number_likes")
    lateinit var numberLikes: Integer

    @SerializedName("number_dislikes")
    lateinit var numberDislikes: Integer

    @SerializedName("date")
    lateinit var date: Date

    @SerializedName("text")
    lateinit var text: String

    @SerializedName("user")
    lateinit var user: User

    @SerializedName("comments")
    lateinit var comments: List<Comment>

    init {
        DaggerDaggerComponent.create().inject(this)
    }

    fun getModel(): MutableList<Model> {
        var models = mutableListOf<Model>()

        models.add(modelFactory.buildFromComment(this))

        if(hasSubComments()) {
            models.addAll(getSubModels())
        }

        return models
    }

    private fun hasSubComments() : Boolean {
        return ::comments.isInitialized && comments.isNotEmpty()
    }

    private fun getSubModels(): MutableList<Model> {
        val models = mutableListOf<Model>()
        if (comments.isNotEmpty()) {
            comments.forEach {
                models.addAll(it.getModel())
            }
        }

        return models
    }

}