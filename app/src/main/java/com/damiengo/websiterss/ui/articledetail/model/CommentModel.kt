package com.damiengo.websiterss.ui.articledetail.model

import java.util.*

class CommentModel(_authorName: String, _text: String, _avatarUrl: String, _date: Date, _level: Int) : Model {

    val authorName = _authorName
    val text       = _text
    val avatarUrl  = _avatarUrl
    val date       = _date
    val level      = _level

}