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
    fun getArticleIdFromUrlOkWithHashtag() {
        assertEquals("1065515", u.getArticleIdFromUrl("https://www.lequipe.fr/Football/Actualites/Om-jacques-henri-eyraud-suspendu-quatre-matches-dont-deux-avec-sursis/1065515#xtor=RSS-1"))
    }

    @Test
    fun getArticleIdFromUrlOkNoHashtag() {
        assertEquals("1065515", u.getArticleIdFromUrl("http://lequipe.fr/Football/Actualites/Om-jacques-henri-eyraud-suspendu-quatre-matches-dont-deux-avec-sursis/1065515"))
    }

    @Test
    fun removeLinksFromTextOneLinkOK() {
        assertEquals("text without links", u.removeLinksFromText("text <a href=\"href_dest\">without</a> links"))
    }

    @Test
    fun removeLinksFromTextTwoLinksOK() {
        assertEquals("text without linkz", u.removeLinksFromText("text <a href=\"href_dest\">without</a> <a href=\"href_dest2\">linkz</a>"))
    }

    @Test
    fun removeLinksFromTextNoLinkOK() {
        assertEquals("text without link", u.removeLinksFromText("text without link"))
    }

    @Test
    fun removeLinksFromTextNoLinkButPOK() {
        assertEquals("text <p>without</p> link", u.removeLinksFromText("text <p>without</p> link"))
    }

}