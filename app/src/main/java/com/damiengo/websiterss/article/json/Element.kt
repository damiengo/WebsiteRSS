package com.damiengo.websiterss.article.json

import com.google.gson.annotations.SerializedName

class Element {

    @SerializedName("libelle")
    lateinit var libelle: String

    fun getLibelleText(): String {
        if(hasLibelle()) {
            return libelle
        }

        return ""
    }

    private fun hasLibelle(): Boolean {
        return ::libelle.isInitialized
    }

}