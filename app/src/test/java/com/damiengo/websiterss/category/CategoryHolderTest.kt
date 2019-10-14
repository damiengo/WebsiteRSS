package com.damiengo.websiterss.category

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CategoryHolderTest {

    private lateinit var h: CategoryHolder
    private lateinit var hEmpty: CategoryHolder

    @Before
    fun setUp() {
        h = CategoryHolder()
        h.categories = TestCategoriesBuilder().build()
        hEmpty = CategoryHolder()
        hEmpty.categories = mapOf()
    }

    @Test
    fun getCurrentTitleOK() {
        assertEquals("Football", h.getCurrentTitle(1))
    }

    @Test
    fun getCurrentTitleNoId() {
        assertEquals("", h.getCurrentTitle(88))
    }

    @Test
    fun getCurrentTitleNoCategories() {
        assertEquals("", hEmpty.getCurrentTitle(0))
    }

    @Test
    fun getCurrentUrlOK() {
        assertEquals("http://www.lequipe.fr/rss/actu_rss_Transferts.xml", h.getCurrentUrl(2))
    }

    @Test
    fun getCurrentUrlNoId() {
        assertEquals("", h.getCurrentUrl(99))
    }

    @Test
    fun getCurrentUrlNoCategories() {
        assertEquals("", hEmpty.getCurrentUrl(0))
    }

    @Test
    fun getDefaultUrlOK() {
        assertEquals("http://www.lequipe.fr/rss/actu_rss.xml", h.getDefaultUrl())
    }

    @Test
    fun getDefaultUrlNoCategories() {
        assertEquals("", hEmpty.getDefaultUrl())
    }

}

class TestCategoriesBuilder : CategoriesBuilder {

    override fun build(): Map<Int, Category> {
        return mapOf(
            0 to Category("Actualité",  "http://www.lequipe.fr/rss/actu_rss.xml"),
            1 to Category("Football",   "http://www.lequipe.fr/rss/actu_rss_Football.xml"),
            2 to Category("Transferts", "http://www.lequipe.fr/rss/actu_rss_Transferts.xml")
        )
    }

}