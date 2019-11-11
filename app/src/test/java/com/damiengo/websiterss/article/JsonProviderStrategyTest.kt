package com.damiengo.websiterss.article

import com.damiengo.websiterss.article.json.*
import com.damiengo.websiterss.ui.articledetail.model.ChapoModel
import com.damiengo.websiterss.ui.articledetail.model.DigitModel
import com.damiengo.websiterss.ui.articledetail.model.EmbedModel
import com.damiengo.websiterss.ui.articledetail.model.ParagraphModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class JsonProviderStrategyTest {

    private lateinit var t: JsonProviderStrategy

    @Before
    fun setUp() {
        t = JsonProviderStrategy()
        t.util = ArticleUtil()
        t.service = ApiTest()
    }

    @Test
    fun readNbModelsOK() {
        runBlocking {
            val models=  t.read("")
            Assert.assertEquals(5, models.size)
        }
    }

    @Test
    fun readChapoOK() {
        runBlocking {
            val models = t.read("")
            val model = models[0]
            Assert.assertThat(model, CoreMatchers.instanceOf(ChapoModel::class.java))
            Assert.assertEquals("Chapo content", (model as ChapoModel).content)
        }
    }

    @Test
    fun readParagraphOK() {
        runBlocking {
            val models = t.read("")
            val model = models[1]
            Assert.assertThat(model, CoreMatchers.instanceOf(ParagraphModel::class.java))
            Assert.assertEquals("Content of the paragraph", (model as ParagraphModel).content)
        }
    }

    @Test
    fun readEmbedOK() {
        runBlocking {
            val models = t.read("")
            val model = models[3]
            Assert.assertThat(model, CoreMatchers.instanceOf(EmbedModel::class.java))
            Assert.assertEquals("<iframe src=\"https://www.test.fr/index.html\"></iframe>", (model as EmbedModel).html)
        }
    }

    @Test
    fun readDigitOK() {
        runBlocking {
            val models = t.read("")
            val model = models[4]
            Assert.assertThat(model, CoreMatchers.instanceOf(DigitModel::class.java))
            Assert.assertEquals("48", (model as DigitModel).title)
            Assert.assertEquals("Content of the digit", model.content)
        }
    }

    class ApiTest : Api {
        override suspend fun getItems(articleId: String): Response<ItemList> {
            val itemList = ItemList()
            val items = mutableListOf<Item>()

            val item1 = Item()
            val object1 = ItemObject()

            val paragraphs = mutableListOf<Paragraph>()

            val par1 = buildParagraph("chapo", "", "Chapo content")
            paragraphs.add(par1)

            val par2 = buildParagraph("text", "", "Content of the paragraph")
            paragraphs.add(par2)

            val par3 = buildParagraph("unknown", "", "Content of the paragraph")
            paragraphs.add(par3)

            val par4 = buildParagraph("embed", "", "<iframe src=\"https://www.test.fr/index.html\"></iframe>")
            paragraphs.add(par4)

            val par5 = buildParagraph("digit", "48", "Content of the digit")
            paragraphs.add(par5)

            object1.paragraphs = paragraphs
            item1.objet = object1

            items.add(item1)

            itemList.items = items

            return Response.success(itemList)
        }

        private fun buildParagraph(layout: String, title: String, content: String) : Paragraph {
            val par = Paragraph()
            par.layout = layout
            par.title = title
            par.content = content

            return par
        }

    }

}