package com.damiengo.websiterss.article

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ArticleUtil @Inject constructor() {


    companion object{

        const val TITLE_CAT_MAX_LEN = 30
        const val RSS_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z"
        const val DATE_HOUR_FORMAT = "HH:mm"
        const val DATE_DAY_HOUR_FORMAT = "dd/MM HH:mm"
        const val DOMAIN_NAME = "lequipe.fr"
        const val URL_SEPARATOR = "/"
        const val CAT_SEPARATOR = " - "

    }

    fun genTitle(title: String?): String {
        title?.let{
            val lastIdx = categoryLastIndex(it)

            if(lastIdx != -1) {
                return it.substring(lastIdx + CAT_SEPARATOR.length)
            }

            return it
        }

        return ""
    }

    fun genCategories(title: String?): MutableList<String> {
        title?.let {
            val lastIdx = categoryLastIndex(it)

            if(lastIdx != -1) {
                return it.substring(0, lastIdx).split(CAT_SEPARATOR).toMutableList()
            }
        }

        return arrayListOf()
    }

    fun genPubDateFromRSS(pubDate: String?): String {
        pubDate?.let {
            if(pubDate == "") {
                return ""
            }
            val inputFormat = SimpleDateFormat(RSS_DATE_FORMAT, Locale.ENGLISH)
            inputFormat.timeZone = TimeZone.getTimeZone("Europe/Paris")
            val inputDate = inputFormat.parse(it)!!
            return genPubDateFromDate(inputDate)
        }

        return ""
    }

    fun genPubDateFromDate(pubDate: Date): String {
        var outputFormat: SimpleDateFormat = this.getOutputFormat(DATE_DAY_HOUR_FORMAT)
        if(DateUtils.isToday(pubDate.time)) {
            outputFormat = this.getOutputFormat(DATE_HOUR_FORMAT)
        }

        return outputFormat.format(pubDate)
    }

    private fun getOutputFormat(format: String): SimpleDateFormat {
        val outputFormat = SimpleDateFormat(format, Locale.ENGLISH)
        outputFormat.timeZone = TimeZone.getTimeZone("Europe/Paris")

        return outputFormat
    }

    fun getArticleIdFromUrl(url: String): String {
        val splitted = url.split(URL_SEPARATOR)

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

    fun getArticleCategorySlugFromUrl(url: String): String {
        val domainIndex = url.indexOf(DOMAIN_NAME)
        val lastSlashIndex = url.lastIndexOf(URL_SEPARATOR)

        if(domainIndex != -1) {
            return url.substring(domainIndex+DOMAIN_NAME.length+1, lastSlashIndex)
        }

        return ""
    }

    private fun categoryLastIndex(title: String): Int {
        var titleCatLength = TITLE_CAT_MAX_LEN
        if(title.length < TITLE_CAT_MAX_LEN) {
            titleCatLength = title.length
        }

        return title.substring(0, titleCatLength).lastIndexOf(CAT_SEPARATOR)
    }

    fun removeLinksFromText(text: String): String {
        return text.replace("<a[^>]*>".toRegex(), "").replace("</a>", "")
    }

}