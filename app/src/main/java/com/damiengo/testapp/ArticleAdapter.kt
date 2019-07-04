package com.damiengo.testapp

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ArticleAdapter(private val dataSource: MutableList<Article>,
                     private val clickListener: (Article) -> Unit) : RecyclerView.Adapter<ArticleViewHolder>() {

    private lateinit var context: Context
    private val hhmmFormat = "HH:mm"

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ArticleViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(context)
        return ArticleViewHolder(inflater, parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article: Article = dataSource[position]

        if( ! this.articleSetted(article)) {
            setArticleCategoryText(article)
            setArticleTime(article)
        }

        GlideApp.with(context)
            .load(article.image)
            .centerCrop()
            .into(holder.getImageView())

        holder.bind(article, clickListener)
    }

    private fun setArticleCategoryText(article: Article) {
        val title = article.title

        val lastIdx = title?.substring(0, 30)!!.lastIndexOf(" - ")

        if(lastIdx != -1) {
            // Setting title
            article.title = title.substring(lastIdx+3)
            // Setting categories
            title.substring(0, lastIdx).split(" - ").iterator().forEach {
                article.addCategory(it)
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setArticleTime(article: Article) {
        var rssFormatter = DateTimeFormatter.RFC_1123_DATE_TIME
        val date = LocalDateTime.parse(article.pubDate, rssFormatter)
        var formatter = DateTimeFormatter.ofPattern(hhmmFormat)
        article.pubDate = date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun articleSetted(article:Article): Boolean {
        var formatter = DateTimeFormatter.ofPattern(hhmmFormat)
        var ok:Boolean = false
        try {
            formatter.parse(article.pubDate)
            ok = true
        }
        catch (e:Exception) {}

        return ok
    }

    override fun getItemCount(): Int = dataSource.size

}