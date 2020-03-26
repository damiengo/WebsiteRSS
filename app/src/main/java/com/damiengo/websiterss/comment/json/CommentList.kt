package com.damiengo.websiterss.comment.json

import com.damiengo.websiterss.article.json.Item
import com.google.gson.annotations.SerializedName

class CommentList {

    @SerializedName("comments")
    lateinit var comments: List<Comment>

}