package com.damiengo.websiterss.article

import com.damiengo.websiterss.ui.articledetail.model.ChapoModel
import com.damiengo.websiterss.ui.articledetail.model.ParagraphModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.instanceOf
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DomProviderStrategyTest {

    private lateinit var t: DomProviderStrategy

    @Before
    fun setUp() {
        t = DomProviderStrategy()
    }

    @Test
    fun getChapoOK() {
        t.articleReader = FileArticleReader()
        runBlocking {
            val models = t.read("lequipe_article.html")
            val model = models[1]
            Assert.assertThat(model, instanceOf(ChapoModel::class.java))
            Assert.assertEquals(
                "Le nouvel entraîneur de la Sampdoria veut éviter au club de Gênes de descendre en Serie B.",
                (model as ChapoModel).content)
        }
    }

    @Test
    fun genChapoNoCssClass() {
        t.articleReader = TextArticleReader()
        runBlocking {
            val models = t.read("<html><body><div class='no_chapi'></class></body></html>")
            val model = models[1]
            Assert.assertThat(model, instanceOf(ChapoModel::class.java))
            Assert.assertEquals("", (model as ChapoModel).content)
        }
    }

    @Test
    fun getDescriptionOK() {
        t.articleReader = FileArticleReader()
        runBlocking {
            val models = t.read("lequipe_article.html")
            val model = models[2]
            Assert.assertThat(model, instanceOf(ParagraphModel::class.java))
            Assert.assertEquals(
                "<!----> \n" +
                        "<i>«&nbsp;Mon objectif est de sauver l'équipe&nbsp;»,</i> a déclaré mardi Claudio Ranieri, le nouvel entraîneur de la Sampdoria. <i>«&nbsp;Je crois qu'il faut faire rêver les fans. Nous sommes derniers, mais cela n'est pas la vraie valeur de cette équipe. Nous devons d'abord restaurer notre confiance en soi. Les contes de fées sont d'abord le résultat d'un travail acharné.&nbsp;»</i><br /><br /><!----> \n" +
                        "L'entraîneur italien a été nommé samedi en remplacement d'Eusebio di Francesco. Il avait déjà remplacé ce même Di Francesco il y a sept mois, à la tête d'un autre club italien, la Roma.<br /><br /><p><b>Ranieri va retrouver la Roma</b></p> \n" +
                        "Hasard du calendrier, il dirigera son premier match sur le banc de la Sampdoria face aux Giallorossi. Double hasard, ce sera aussi le 68e anniversaire de Ranieri. «&nbsp;<i>Ce n'est pas un tout à fait un hasard pour moi</i>, a-t-il déclaré, <i>je suis heureux de débuter face à la Roma dont je suis un grand fan. Mais le professionnalisme passe avant tout.&nbsp;»</i><br /><br /><!----> \n" +
                        "Cette année, la Sampdoria n'a remporté qu'un match en sept rencontres. Elle pointe à deux points de son grand rival, l'autre club de la ville&nbsp;: le Genoa, avant-dernier.<br /><br />",
                (model as ParagraphModel).content
            )
        }
    }

    @Test
    fun genDescriptionNoCssClass() {
        t.articleReader = TextArticleReader()
        runBlocking {
            val models = t.read("<html><body><div class='no_paragraph'></div></body></html>")
            val model = models[2]
            Assert.assertThat(model, instanceOf(ParagraphModel::class.java))
            Assert.assertEquals("", (model as ParagraphModel).content)
        }
    }

    @Test
    fun genDescriptionPRemoved() {
        t.articleReader = TextArticleReader()
        runBlocking {
            val models = t.read("<html><body><div class='article__body'><div class='Paragraph'><p>Inside p.</p><span>Outside p.</span></div></div></body></html>")
            val model = models[2]
            Assert.assertThat(model, instanceOf(ParagraphModel::class.java))
            Assert.assertEquals("Inside p.\n" +
                    "<span>Outside p.</span><br /><br />", (model as ParagraphModel).content)
        }
    }

    @Test
    fun genDescriptionARemoved() {
        t.articleReader = TextArticleReader()
        runBlocking {
            val models = t.read("<html><body><div class='article__body'><div class='Paragraph'>A tag <a href='http://link'>removed</a></div></div></body></html>")
            val model = models[2]
            Assert.assertThat(model, instanceOf(ParagraphModel::class.java))
            Assert.assertEquals("A tag \n" +
                    "removed<br /><br />", (model as ParagraphModel).content)
        }
    }

    @Test
    fun genDescriptionH3Removed() {
        t.articleReader = TextArticleReader()
        runBlocking {
            val models = t.read("<html><body><div class='article__body'><div class='Paragraph'>H3 tag <h3>removed</h3></div></div></body></html>")
            val model = models[2]
            Assert.assertThat(model, instanceOf(ParagraphModel::class.java))
            Assert.assertEquals("H3 tag \n" +
                    "<p><b>removed</b></p><br /><br />", (model as ParagraphModel).content)
        }
    }

    class FileArticleReader : ArticleReader {
        override fun read(url: String): Document {
            val res = javaClass.classLoader!!.getResourceAsStream(url)
            return Jsoup.parse(res, "UTF-8", "www.lequipe.fr")
        }
    }

    class TextArticleReader : ArticleReader {
        override fun read(url: String): Document {
            return Jsoup.parse(url)
        }
    }

}
