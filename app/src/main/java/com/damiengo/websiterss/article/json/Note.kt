package com.damiengo.websiterss.article.json

import com.google.gson.annotations.SerializedName

class Note {

    @SerializedName("label")
    lateinit var label: String

    @SerializedName("rating")
    lateinit var rating: String

}