package com.damiengo.websiterss.article

class ArticleUtil {

    companion object{

        const val titleCatMaxLength = 30

    }

    fun genTitle(title: String): String {
        val lastIdx = categoryLastIndex(title)

        if(lastIdx != -1) {
            return title.substring(lastIdx+3)
        }

        return title
    }

    fun genCategories(title: String): MutableList<String> {
        val lastIdx = categoryLastIndex(title)

        if(lastIdx != -1) {
            return title.substring(0, lastIdx).split(" - ").toMutableList()
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

        return title?.substring(0, titleCatLength)!!.lastIndexOf(" - ")
    }

}