package com.damiengo.websiterss.article

import com.damiengo.websiterss.article.json.*
import com.damiengo.websiterss.ui.articledetail.model.*
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
            Assert.assertEquals(13, models.size)
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
    fun readEmbedEmptyOK() {
        runBlocking {
            val models = t.read("")
            val model = models[3]
            Assert.assertThat(model, CoreMatchers.instanceOf(EmptyModel::class.java))
        }
    }

    @Test
    fun readEmbedTwitterOK() {
        runBlocking {
            val models = t.read("")
            val model = models[12]
            Assert.assertThat(model, CoreMatchers.instanceOf(TwitterModel::class.java))
            Assert.assertEquals("Un rêve incroyable qui se réalise <br>CHAMPIONNES DU MONDE Corinne Dubreuil/ FFT",
                (model as TwitterModel).content)
            Assert.assertEquals("pic.twitter.com/arn4kEUsFO", model.picture)
            Assert.assertEquals("November 10, 2019", model.date)
            Assert.assertEquals("https://twitter.com/KikiMladenovic/status/1193623935155224577?ref_src=twsrc%5Etfw", model.link)
            Assert.assertEquals("— Kristina Mladenovic (@KikiMladenovic)", model.author)
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

    @Test
    fun readDigitNoTitleOK() {
        runBlocking {
            val models = t.read("")
            val model = models[8]
            Assert.assertThat(model, CoreMatchers.instanceOf(DigitModel::class.java))
            Assert.assertEquals("", (model as DigitModel).title)
            Assert.assertEquals("Content of the digit no title", model.content)
        }
    }

    @Test
    fun readCitationOK() {
        runBlocking {
            val models = t.read("")
            val model = models[5]
            Assert.assertThat(model, CoreMatchers.instanceOf(CitationModel::class.java))
            Assert.assertEquals("Content of the citation", (model as CitationModel).content)
            Assert.assertEquals("Caption...", model.caption)
        }
    }

    @Test
    fun readCitationNoCaptionOK() {
        runBlocking {
            val models = t.read("")
            val model = models[9]
            Assert.assertThat(model, CoreMatchers.instanceOf(CitationModel::class.java))
            Assert.assertEquals("Content of the citation no caption", (model as CitationModel).content)
            Assert.assertEquals("", model.caption)
        }
    }

    @Test
    fun readNoteOK() {
        runBlocking {
            val models = t.read("")
            val model = models[6]
            Assert.assertThat(model, CoreMatchers.instanceOf(NoteModel::class.java))
            Assert.assertEquals("Content of the note", (model as NoteModel).content)
            Assert.assertEquals("Zidane", model.player)
            Assert.assertEquals("8.5", model.rating)
        }
    }

    @Test
    fun readNoteNoPlayerOK() {
        runBlocking {
            val models = t.read("")
            val model = models[10]
            Assert.assertThat(model, CoreMatchers.instanceOf(NoteModel::class.java))
            Assert.assertEquals("Content of the note", (model as NoteModel).content)
            Assert.assertEquals("", model.player)
            Assert.assertEquals("8.5", model.rating)
        }
    }

    @Test
    fun readNoteNoRatingOK() {
        runBlocking {
            val models = t.read("")
            val model = models[11]
            Assert.assertThat(model, CoreMatchers.instanceOf(NoteModel::class.java))
            Assert.assertEquals("Content of the note", (model as NoteModel).content)
            Assert.assertEquals("Zizou", model.player)
            Assert.assertEquals("", model.rating)
        }
    }

    @Test
    fun readFocusOK() {
        runBlocking {
            val models = t.read("")
            val model = models[7]
            Assert.assertThat(model, CoreMatchers.instanceOf(FocusModel::class.java))
            Assert.assertEquals("Content of the focus", (model as FocusModel).content)
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

            val par6 = buildParagraph("citation", "", "Content of the citation")
            par6.caption = "Caption..."
            paragraphs.add(par6)

            val par7 = buildParagraph("note", "", "Content of the note")
            val note = Note()
            note.label = "Zidane"
            note.rating = "8.5"
            par7.note = note
            paragraphs.add(par7)

            val par8 = buildParagraph("focus", "", "Content of the focus")
            paragraphs.add(par8)

            val par9 = Paragraph()
            par9.layout = "digit"
            par9.content = "Content of the digit no title"
            paragraphs.add(par9)

            val par10 = Paragraph()
            par10.layout = "citation"
            par10.content = "Content of the citation no caption"
            paragraphs.add(par10)

            val par11 = buildParagraph("note", "", "Content of the note")
            val note1 = Note()
            note1.rating = "8.5"
            par11.note = note1
            paragraphs.add(par11)

            val par12 = buildParagraph("note", "", "Content of the note")
            val note2 = Note()
            note2.label = "Zizou"
            par12.note = note2
            paragraphs.add(par12)

            val par13 = buildParagraph("embed", "", "<blockquote class=\"twitter-tweet\">\n" +
                    "<p lang=\"fr\" dir=\"ltr\">\n" +
                    "Un rêve incroyable qui se réalise <br>CHAMPIONNES DU MONDE Corinne Dubreuil/ FFT\n" +
                    "<a href=\"https://t.co/arn4kEUsFO\">pic.twitter.com/arn4kEUsFO</a>\n" +
                    "</p>&mdash; Kristina Mladenovic (@KikiMladenovic) <a href=\"https://twitter.com/KikiMladenovic/status/1193623935155224577?ref_src=twsrc%5Etfw\">November 10, 2019</a>\n" +
                    "</blockquote>")
            paragraphs.add(par13)

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