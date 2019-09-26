package com.damiengo.websiterss

import com.damiengo.websiterss.ui.home.MainActivity
import junit.framework.Assert.assertTrue
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Robolectric
import org.junit.Before
import org.junit.Test
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private lateinit var activity: MainActivity

    @Before
    @Throws(Exception::class)
    fun setUp() {
        // Use a http shadow

        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    fun shouldDisplayAList() {
        assertTrue(false)
    }

}