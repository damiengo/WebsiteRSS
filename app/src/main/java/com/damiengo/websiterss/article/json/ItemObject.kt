package com.damiengo.websiterss.article.json

import com.google.gson.annotations.SerializedName

class ItemObject {

    @SerializedName("paragraphs")
    lateinit var paragraphs: List<Paragraph>

}