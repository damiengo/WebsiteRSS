package com.damiengo.websiterss.article

import com.damiengo.websiterss.article.json.Api
import com.damiengo.websiterss.article.json.Item
import com.damiengo.websiterss.article.json.ItemList
import com.damiengo.websiterss.article.json.Paragraph
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.ui.articledetail.model.ParagraphModel
import javax.inject.Inject

class JsonProviderStrategy : ProviderStrategy {

    @Inject
    lateinit var service: Api

    override suspend fun read(articleId: String): MutableList<Model> {
        var models = mutableListOf<Model>()

        val itemList = service.getItems(articleId)

        itemList?.let {list: ItemList ->
            if(list.items.isNotEmpty()) {
                list.items.forEach {item: Item ->
                    if(item.objet.paragraphs.isNotEmpty()) {
                        item.objet.paragraphs.forEach {paragraph: Paragraph ->
                            val model = ParagraphModel(paragraph.content)
                            models.add(model)
                        }
                    }
                }
            }
        }

        return models
    }

}