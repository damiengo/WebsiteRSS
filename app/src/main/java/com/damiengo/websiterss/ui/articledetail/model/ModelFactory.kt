package com.damiengo.websiterss.ui.articledetail.model

import com.damiengo.websiterss.article.json.Element
import com.damiengo.websiterss.article.json.Paragraph
import org.jsoup.Jsoup
import java.util.*

class ModelFactory {

    companion object {
        const val LAYOUT_CHAPO: String = "chapo"
        const val LAYOUT_PARAGRAPH: String = "text"
        const val LAYOUT_EMBED: String = "embed"
        const val LAYOUT_DIGIT: String = "digit"
        const val LAYOUT_CITATION: String = "citation"
        const val LAYOUT_NOTE: String = "note"
        const val LAYOUT_FOCUS: String = "focus"
    }

    fun buildFromParagraph(p: Paragraph): Model {
        return when(p.layout) {
            LAYOUT_CHAPO     -> ChapoModel(p.getContentText())
            LAYOUT_PARAGRAPH -> ParagraphModel(p.getContentText())
            LAYOUT_EMBED     -> buildEmbedFromParagraph(p)
            LAYOUT_DIGIT     -> DigitModel(p.getTitleText(), p.getContentText())
            LAYOUT_CITATION  -> CitationModel(p.getContentText(), p.getCaptionText())
            LAYOUT_NOTE      -> NoteModel(p.getContentText(), p.getNoteLabelText(), p.getNoteRatingText())
            LAYOUT_FOCUS     -> FocusModel(p.getContentText())
            else             -> EmptyModel()
        }
    }

    private fun buildEmbedFromParagraph(p: Paragraph): Model {
        if(p.content.contains("twitter-tweet")) {
            return buildTwitterModelFromParagraph(p)
        }

        return EmptyModel()
    }

    private fun buildTwitterModelFromParagraph(p: Paragraph): TwitterModel {
        val htmlTweet = Jsoup.parse(p.getContentText())
        val content = htmlTweet.select("blockquote > p").html().replace("<a[^>]*>(.*)</a>".toRegex(), "$1").trim()
        val picture = ""
        val date = htmlTweet.select("blockquote > a").text().trim()
        val link = htmlTweet.select("blockquote > a").attr("href")
        val author = htmlTweet.select("blockquote").html().replace("<p[^>]*>.*</p>".toRegex(), "").replace("<a[^>]*>.*<\\/a>".toRegex(), "").trim()

        val model = TwitterModel()

        model.content = content
        model.picture = picture
        model.date = date
        model.link = link
        model.author = author

        return model
    }

    fun buildTitle(title: String): TitleModel {
        return TitleModel(title)
    }

    fun buildInfoModel(pubDate: Date, elements: List<Element>): InfoModel {
        return InfoModel(pubDate, elements.joinToString(separator = " • ") { it.getLibelleText() })
    }

    fun buildTitleImageModel(url: String): TitleImageModel {
        val width = "1248"
        val height = "832"
        val quality = "75"

        val realUrl = url.replace("{width}", width)
                                .replace("{height}", height)
                                .replace("{quality}", quality)

        return TitleImageModel(realUrl)
    }

}