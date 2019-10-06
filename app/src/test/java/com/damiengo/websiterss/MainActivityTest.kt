package com.damiengo.websiterss

import androidx.recyclerview.widget.RecyclerView
import com.damiengo.websiterss.ui.home.MainActivity
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Robolectric
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.httpclient.FakeHttp
import java.io.File

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
        //var feedActu = MainActivityTest::class.java.getResource("feed_actu.xml").readText()
        var feedActu = File("/home/dam/AndroidStudioProjects/EquipeRSS/app/src/test/res/feed_actu.xml").readText()
        FakeHttp.addPendingHttpResponse(200, feedActu)
        var listArticles = activity.findViewById<RecyclerView>(R.id.list_articles)
        assert(listArticles.adapter!!.itemCount == 8)
    }

}