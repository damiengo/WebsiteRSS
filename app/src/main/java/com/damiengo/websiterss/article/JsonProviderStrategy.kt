package com.damiengo.websiterss.article

import com.damiengo.websiterss.article.json.Api
import com.damiengo.websiterss.article.json.Item
import com.damiengo.websiterss.article.json.ItemList
import com.damiengo.websiterss.article.json.Paragraph
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.ui.articledetail.model.ParagraphModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class JsonProviderStrategy: Callback<ItemList> {

    @Inject
    lateinit var service: Api

    fun read(articleId: String) {
        val call: Call<ItemList> = service.getItems(articleId)

        call.enqueue(this)


    }

    override fun onFailure(call: Call<ItemList>, t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
        var models = mutableListOf<Model>()
        if(response.isSuccessful) {
            val itemList = response.body()
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
        }
    }

}