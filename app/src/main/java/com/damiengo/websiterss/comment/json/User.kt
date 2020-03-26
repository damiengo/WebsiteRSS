package com.damiengo.websiterss.comment.json

import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("pseudo")
    lateinit var pseudo: String

    @SerializedName("avatar_url")
    lateinit var avatarUrl: String

}