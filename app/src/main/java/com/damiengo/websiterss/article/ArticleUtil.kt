package com.damiengo.websiterss.article

/**
 * Utils for article.
 */
class ArticleUtil {

    companion object{

        const val titleCatMaxLength = 30

    }

    fun setTitle(title: String): String {
        val lastIdx = categoryLastIndex(title)

        if(lastIdx != -1) {
            return title.substring(lastIdx+3)
        }

        return title
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