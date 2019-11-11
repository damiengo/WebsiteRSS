package com.damiengo.websiterss.ui.articledetail.model

import com.damiengo.websiterss.article.json.Paragraph

class ModelFactory {

    companion object {
        val LAYOUT_CHAPO: String = "chapo"
        val LAYOUT_PARAGRAPH: String = "text"
        val LAYOUT_EMBED: String = "embed"
        val LAYOUT_DIGIT: String = "digit"
    }

    fun buildFromParagraph(p: Paragraph): Model {
        return when(p.layout) {
            LAYOUT_CHAPO -> ChapoModel(p.content)
            LAYOUT_PARAGRAPH -> ParagraphModel(p.getContentText())
            LAYOUT_EMBED -> EmbedModel(p.getContentText())
            LAYOUT_DIGIT -> DigitModel(p.title, p.getContentText())
            else -> EmptyModel()
        }
    }

}