package com.damiengo.websiterss.article.json

import com.google.gson.annotations.SerializedName

class Subhead {

    @SerializedName("elements")
    lateinit var elements: List<Element>

}