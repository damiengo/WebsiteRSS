package com.damiengo.websiterss.article

import com.damiengo.websiterss.ui.articledetail.model.ChapoModel
import com.damiengo.websiterss.ui.articledetail.model.Model
import com.damiengo.websiterss.ui.articledetail.model.ParagraphModel
import com.damiengo.websiterss.util.DaggerDaggerComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.nodes.Document
import javax.inject.Inject

class DomProviderStrategy : ProviderStrategy {

    @Inject
    lateinit var util: ArticleUtil

    @Inject
    lateinit var articleReader: ArticleReader

    private lateinit var dom: Document

    init {
        DaggerDaggerComponent.create().inject(this)
    }

    override suspend fun read(url: String): MutableList<Model> {
        dom = withContext(Dispatchers.IO) {
            articleReader.read(url)
        }

        var models = mutableListOf<Model>()
        models.add(ChapoModel(getChapo()))
        models.add(ParagraphModel(getDescription()))

        return models
    }

    private fun getChapo(): String {
        return dom.select(".Article__chapo").text()
    }

    private fun getDescription(): String {
        val builder = StringBuilder()
        dom.select(".article__body .Paragraph").forEach { ele ->
            builder.append(ele.html()).append("<br /><br />")
        }
        var htmlDesc = builder.toString()
        // Delete paragraph
        htmlDesc = htmlDesc.replace("<p[^>]*>".toRegex(), "")
        htmlDesc = htmlDesc.replace("</p>", "")
        // Delete links
        htmlDesc = htmlDesc.replace("<a[^>]*>".toRegex(), "")
        htmlDesc = htmlDesc.replace("</a>", "")
        // Replace title
        htmlDesc = htmlDesc.replace("<h3[^>]*>".toRegex(), "<p><b>")
        htmlDesc = htmlDesc.replace("</h3>", "</b></p>")

        return htmlDesc
    }
}