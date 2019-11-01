package com.damiengo.websiterss.article.json

import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.ui.articledetail.model.ModelFactory
import com.damiengo.websiterss.util.DaggerDaggerComponent
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class Paragraph {

    @Inject
    lateinit var modelFactory: ModelFactory

    @SerializedName("__type")
    lateinit var type: String

    @SerializedName("content")
    lateinit var content: String

    @SerializedName("is_focus")
    var isFocus: Boolean = false

    @SerializedName("layout")
    lateinit var layout: String

    @SerializedName("number")
    var number: Int = 0

    @SerializedName("title")
    lateinit var title: String

    init {
        DaggerDaggerComponent.create().inject(this)
    }

    fun getModel(): Model {
        return modelFactory.buildFromParagraph(this)
    }

    fun getContentText(): String {
        if(::content.isInitialized) {
            return content
        }

        return ""
    }

}