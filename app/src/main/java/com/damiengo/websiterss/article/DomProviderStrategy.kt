package com.damiengo.websiterss.article

import com.damiengo.websiterss.App
import com.damiengo.websiterss.R
import com.damiengo.websiterss.util.DaggerDaggerComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import javax.inject.Inject

class DomProviderStrategy : ProviderStrategy {

    @Inject
    lateinit var util: ArticleUtil

    private lateinit var dom: Document

    init {
        DaggerDaggerComponent.create().inject(this)
    }

    override suspend fun read(url: String) {
        dom = withContext(Dispatchers.IO) {
            Jsoup.connect(url)
                .userAgent(App.getRes().getString(R.string.user_agent))
                .referrer(App.getRes().getString(R.string.referrer))
                .get()
        }
    }

    override fun getChapo(): String {
        return util.genChapoFromDom(dom)
    }

    override fun getDescription(): String {
        return util.genDescriptionFromDom(dom)
    }
}