package com.damiengo.websiterss.article

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArticleUtilTest {

    private lateinit var u: ArticleUtil

    @Before
    fun setUp() {
        u = ArticleUtil()
    }

    @Test
    fun genTitleWithDashLessThanMaxSize() {
        assertEquals("dashes", u.genTitle("Title with - dashes"))
    }

    @Test
    fun genTitleWithDashesLessThanMaxSize() {
        assertEquals("dash 3", u.genTitle("Title with - dash1 - dash 2 - dash 3"))
    }

    @Test
    fun genTitleWithDashesLessAndMoreThanMaxSize() {
        assertEquals("dash 3 - dash 4", u.genTitle("Title with - dash1 - dash 2 - dash 3 - dash 4"))
    }

    @Test
    fun genTitleWithDashMoreThanMaxSize() {
        assertEquals("Title with dashes and a size of title larger than - 30 characters", u.genTitle("Title with dashes and a size of title larger than - 30 characters"))
    }

    @Test
    fun genTitleWithoutDashes() {
        assertEquals("Title without dashes", u.genTitle("Title without dashes"))
    }

    @Test
    fun genTitleEmpty() {
        assertEquals("", u.genTitle(""))
    }

    @Test
    fun genTitleNull() {
        assertEquals("", u.genTitle(null))
    }

    @Test
    fun genCategoriesOneCategory() {
        assertEquals(mutableListOf("Category 1"), u.genCategories("Category 1 - A title with only one category"))
    }

    @Test
    fun genCategoriesMoreThanOneCategory() {
        assertEquals(mutableListOf("Category 1", "Category 2"), u.genCategories("Category 1 - Category 2 - A title with several categories"))
    }

    @Test
    fun genCategoriesNoCategory() {
        assertEquals(mutableListOf<String>(), u.genCategories("A title with no category detected because to long title - Title"))
    }

    @Test
    fun genCategoriesNullCategory() {
        assertEquals(mutableListOf<String>(), u.genCategories(null))
    }

    @Test
    fun getPubDateFormatOk() {
        assertEquals("20:51", u.genPubDate("Wed, 09 Oct 2019 20:51:02 +0200"))
    }

    @Test
    fun genPubDateNoDate() {
        assertEquals("", u.genPubDate(""))
    }

    @Test
    fun genPubDateNullDate() {
        assertEquals("", u.genPubDate(null))
    }

    @Test
    fun genChapoFromDomOK() {
        assertEquals("Le nouvel entraîneur de la Sampdoria veut éviter au club de Gênes de descendre en Serie B.", u.genChapoFromDom(getDomFromFile()))
    }

    @Test
    fun genChapoNoCssClass() {
        assertEquals("", u.genChapoFromDom(Jsoup.parse("<html><body><div class='no_chapi'></class></body></html>")))
    }

    @Test
    fun genDescriptionFromDomOK() {
        assertEquals("<!----> \n" +
                "<i>«&nbsp;Mon objectif est de sauver l'équipe&nbsp;»,</i> a déclaré mardi Claudio Ranieri, le nouvel entraîneur de la Sampdoria. <i>«&nbsp;Je crois qu'il faut faire rêver les fans. Nous sommes derniers, mais cela n'est pas la vraie valeur de cette équipe. Nous devons d'abord restaurer notre confiance en soi. Les contes de fées sont d'abord le résultat d'un travail acharné.&nbsp;»</i><br /><br /><!----> \n" +
                "L'entraîneur italien a été nommé samedi en remplacement d'Eusebio di Francesco. Il avait déjà remplacé ce même Di Francesco il y a sept mois, à la tête d'un autre club italien, la Roma.<br /><br /><p><b>Ranieri va retrouver la Roma</b></p> \n" +
                "Hasard du calendrier, il dirigera son premier match sur le banc de la Sampdoria face aux Giallorossi. Double hasard, ce sera aussi le 68e anniversaire de Ranieri. «&nbsp;<i>Ce n'est pas un tout à fait un hasard pour moi</i>, a-t-il déclaré, <i>je suis heureux de débuter face à la Roma dont je suis un grand fan. Mais le professionnalisme passe avant tout.&nbsp;»</i><br /><br /><!----> \n" +
                "Cette année, la Sampdoria n'a remporté qu'un match en sept rencontres. Elle pointe à deux points de son grand rival, l'autre club de la ville&nbsp;: le Genoa, avant-dernier.<br /><br />",
            u.genDescriptionFromDom(getDomFromFile()).toString())
    }

    @Test
    fun genDescriptionNoCssClass() {
        assertEquals("", u.genDescriptionFromDom(Jsoup.parse("<html><body><div class='no_paragraph'></div></body></html>")))
    }

    @Test
    fun genDescriptionPRemoved() {
        assertEquals("Inside p.\n" +
                "<span>Outside p.</span><br /><br />", u.genDescriptionFromDom(Jsoup.parse("<html><body><div class='article__body'><div class='Paragraph'><p>Inside p.</p><span>Outside p.</span></div></div></body></html>")))
    }

    @Test
    fun genDescriptionARemoved() {
        assertEquals("A tag \n" +
                "removed<br /><br />", u.genDescriptionFromDom(Jsoup.parse("<html><body><div class='article__body'><div class='Paragraph'>A tag <a href='http://link'>removed</a></div></div></body></html>")))
    }

    @Test
    fun genDescriptionH3Removed() {
        assertEquals("H3 tag \n" +
                "<p><b>removed</b></p><br /><br />", u.genDescriptionFromDom(Jsoup.parse("<html><body><div class='article__body'><div class='Paragraph'>H3 tag <h3>removed</h3></div></div></body></html>")))
    }

    private fun getDomFromFile(): Document {
        val res = javaClass.classLoader.getResourceAsStream("lequipe_article.html")
        return Jsoup.parse(res, "UTF-8", "www.lequipe.fr")
    }

}