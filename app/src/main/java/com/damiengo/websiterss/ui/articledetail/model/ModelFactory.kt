package com.damiengo.websiterss.ui.articledetail.model

import com.damiengo.websiterss.article.json.Paragraph

class ModelFactory {

    companion object {
        val LAYOUT_CHAPO: String = "chapo"
        val LAYOUT_PARAGRAPH: String = "text"
    }

    fun buildFromParagraph(p: Paragraph): Model {
        return when(p.layout) {
            LAYOUT_CHAPO -> ChapoModel(p.content)
            LAYOUT_PARAGRAPH -> ParagraphModel(p.getContentText())
            else -> ParagraphModel("")
        }
    }

}