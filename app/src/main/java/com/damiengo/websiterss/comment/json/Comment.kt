package com.damiengo.websiterss.comment.json

import com.damiengo.websiterss.article.json.ItemObject
import com.google.gson.annotations.SerializedName
import java.util.*

class Comment {

    @SerializedName("number_likes")
    lateinit var numberLikes: Integer

    @SerializedName("number_dislikes")
    lateinit var numberDislikes: Integer

    @SerializedName("date")
    lateinit var date: Date

    @SerializedName("text")
    lateinit var text: String

    @SerializedName("user")
    lateinit var user: User

    @SerializedName("comments")
    lateinit var comments: List<Comment>

}