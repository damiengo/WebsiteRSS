package com.damiengo.websiterss.article.json

import com.google.gson.annotations.SerializedName

class Paragraph {

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

}