package com.damiengo.websiterss.article.json

import com.damiengo.websiterss.di.DaggerDaggerComponent
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.ui.articledetail.model.ModelFactory
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

    @SerializedName("caption")
    lateinit var caption: String

    @SerializedName("note")
    lateinit var note: Note

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

    fun getTitleText(): String {
        if(::title.isInitialized) {
            return title
        }

        return ""
    }

    fun getCaptionText(): String {
        if(::caption.isInitialized) {
            return caption
        }

        return ""
    }

    fun getNoteLabelText(): String {
        return note.getLabelText()
    }

    fun getNoteRatingText(): String {
        return note.getRatingText()
    }

}