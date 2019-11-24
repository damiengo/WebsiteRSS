package com.damiengo.websiterss.article

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ArticleUtil @Inject constructor() {

    companion object{

        const val titleCatMaxLength = 30
        const val inputDateFormat = "EEE, dd MMM yyyy HH:mm:ss z"
        const val outputValFormat = "HH:mm"

    }

    fun genTitle(title: String?): String {
        title?.let{
            val lastIdx = categoryLastIndex(it)

            if(lastIdx != -1) {
                return it.substring(lastIdx+3)
            }

            return it
        }

        return ""
    }

    fun genCategories(title: String?): MutableList<String> {
        title?.let {
            val lastIdx = categoryLastIndex(it)

            if(lastIdx != -1) {
                return it.substring(0, lastIdx).split(" - ").toMutableList()
            }
        }

        return arrayListOf()
    }

    fun genPubDate(pubDate: String?): String {
        pubDate?.let {
            if(pubDate == "") {
                return ""
            }
            val inputFormat = SimpleDateFormat(inputDateFormat, Locale.ENGLISH)
            inputFormat.timeZone = TimeZone.getTimeZone("Europe/Paris")
            val outputFormat = SimpleDateFormat(outputValFormat, Locale.ENGLISH)
            outputFormat.timeZone = TimeZone.getTimeZone("Europe/Paris")
            return outputFormat.format(inputFormat.parse(it)!!)
        }

        return ""
    }

    fun getArticleIdFromUrl(url: String): String {
        val splitted = url.split("/")

        if(splitted.isNotEmpty()) {
            val urlEnd = splitted.last()
            val hashtagPos = urlEnd.indexOf("#")
            if(hashtagPos > 0) {
                return urlEnd.substring(0, hashtagPos)
            }

            return urlEnd
        }

        return ""
    }

    private fun categoryLastIndex(title: String): Int {
        var titleCatLength = titleCatMaxLength
        if(title.length < titleCatMaxLength) {
            titleCatLength = title.length
        }

        return title.substring(0, titleCatLength).lastIndexOf(" - ")
    }

    fun removeLinksFromText(text: String): String {
        return text.replace("<a[^>]*>".toRegex(), "").replace("</a>", "")
    }

}