package com.damiengo.websiterss.article.json

import com.google.gson.annotations.SerializedName

class Note {

    @SerializedName("label")
    lateinit var label: String

    @SerializedName("rating")
    lateinit var rating: String

    fun getLabelText(): String {
        if(::label.isInitialized) {
            return label
        }

        return ""
    }

    fun getRatingText(): String {
        if(::rating.isInitialized) {
            return rating
        }

        return ""
    }

}