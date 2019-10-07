package com.damiengo.websiterss.article

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
    fun genCategoriesOneCategory() {
        assertEquals(mutableListOf("Category 1"), u.genCategories("Category 1 - A title with only one category"))
    }

    @Test
    fun genCategoriesMoreThanOneCategory() {
        assertEquals(mutableListOf("Category 1", "Category 2"), u.genCategories("Category 1 - Category 2 - A title with several categories"))
    }

    @Test
    fun genCategoriesNoCategory() {
        assertEquals(mutableListOf<String>(), u.genCategories("A title with no category detected because to lon title - Title"))
    }

}