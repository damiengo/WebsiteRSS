package com.damiengo.websiterss.article

import javax.inject.Inject

class ArticleUtil @Inject constructor() {

    companion object{

        const val titleCatMaxLength = 30

    }

    fun genTitle(title: String?): String {
        title?.let{
            val lastIdx = categoryLastIndex(title)

            if(lastIdx != -1) {
                return title.substring(lastIdx+3)
            }

            return title
        }

        return ""
    }

    fun genCategories(title: String?): MutableList<String> {
        title?.let {
            val lastIdx = categoryLastIndex(title)

            if(lastIdx != -1) {
                return title.substring(0, lastIdx).split(" - ").toMutableList()
            }
        }

        return arrayListOf()
    }

    /**
     * Last index of dash in title.
     */
    private fun categoryLastIndex(title: String): Int {
        var titleCatLength = titleCatMaxLength
        if(title.length < titleCatMaxLength) {
            titleCatLength = title.length
        }

        return title.substring(0, titleCatLength).lastIndexOf(" - ")
    }

}