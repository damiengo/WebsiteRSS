package com.damiengo.websiterss.article.json

import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("objet")
    lateinit var objet: ItemObject

}