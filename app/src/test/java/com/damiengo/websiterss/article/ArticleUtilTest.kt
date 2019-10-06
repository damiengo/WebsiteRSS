package com.damiengo.websiterss.article

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ArticleUtilTest {

    private lateinit var u: ArticleUtil

    @Before
    fun setUp() {
        u = ArticleUtil()
    }

    @Test
    fun testSetTitleWithDashLessThanMaxSize() {
        assertEquals("dashes", u.setTitle("Title with - dashes"))
    }

    @Test
    fun testSetTitleWithDashMoreThanMaxSize() {
        assertEquals("30 characterz", u.setTitle("Title with dashes and a size of title larger than - 30 characters"))
    }

    @Test
    fun testSetTitleWithoutDashes() {
        assertEquals("Title without dashes", u.setTitle("Title without dashes"))
    }

}