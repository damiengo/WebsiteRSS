package com.damiengo.websiterss.ui.articledetail.model

import com.damiengo.websiterss.article.json.Paragraph

class ModelFactory {

    companion object {
        val LAYOUT_CHAPO: String = "chapo"
        val LAYOUT_PARAGRAPH: String = "text"
        val LAYOUT_EMBED: String = "embed"
        val LAYOUT_DIGIT: String = "digit"
        val LAYOUT_CITATION: String = "citation"
        val LAYOUT_NOTE: String = "note"
        val LAYOUT_FOCUS: String = "focus"
    }

    fun buildFromParagraph(p: Paragraph): Model {
        return when(p.layout) {
            LAYOUT_CHAPO     -> ChapoModel(p.getContentText())
            LAYOUT_PARAGRAPH -> ParagraphModel(p.getContentText())
            LAYOUT_EMBED     -> EmbedModel(p.getContentText())
            LAYOUT_DIGIT     -> DigitModel(p.getTitleText(), p.getContentText())
            LAYOUT_CITATION  -> CitationModel(p.getContentText(), p.getCaptionText())
            LAYOUT_NOTE      -> NoteModel(p.getContentText(), p.getNoteLabelText(), p.getNoteRatingText())
            LAYOUT_FOCUS     -> FocusModel(p.getContentText())
            else             -> EmptyModel()
        }
    }

}