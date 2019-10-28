package com.damiengo.websiterss.article.json

import com.google.gson.annotations.SerializedName

class ItemList {

    @SerializedName("items")
    lateinit var items: List<Item>

}