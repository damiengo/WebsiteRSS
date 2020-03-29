package com.damiengo.websiterss.comment.json

import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("pseudo")
    lateinit var pseudo: String

    @SerializedName("avatar_url")
    lateinit var avatarUrl: String

    fun getPseudoText(): String {
        if(::pseudo.isInitialized) {
            return pseudo
        }

        return ""
    }

    fun getAvatarUrlText(): String {
        if(::avatarUrl.isInitialized) {
            return avatarUrl
        }

        return ""
    }

}