package com.damiengo.websiterss.ui.articledetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.damiengo.websiterss.R
import com.damiengo.websiterss.ui.articledetail.model.*

class ArticleDetailAdapter(private val context: Context): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var adapterDataList = mutableListOf<Model>()

    companion object {
        private const val TYPE_PARAGRAPH = 0
        private const val TYPE_CHAPO     = 1
        private const val TYPE_EMPTY     = 2
        private const val TYPE_EMBED     = 3
        private const val TYPE_DIGIT     = 4
    }

    fun addModel(model: Model) {
        adapterDataList.add(model)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_PARAGRAPH -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_paragraph_item, parent, false)
                ParagraphViewHolder(view)
            }
            TYPE_CHAPO -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_chapo_item, parent, false)
                ChapoViewHolder(view)
            }
            TYPE_EMBED -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_embed_item, parent, false)
                EmbedViewHolder(view)
            }
            TYPE_DIGIT -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_digit_item, parent, false)
                DigitViewHolder(view)
            }
            TYPE_EMPTY -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_empty_item, parent, false)
                return EmptyViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        when (holder) {
            is ParagraphViewHolder -> holder.bind(element as ParagraphModel)
            is ChapoViewHolder     -> holder.bind(element as ChapoModel)
            is EmbedViewHolder     -> holder.bind(element as EmbedModel)
            is DigitViewHolder     -> holder.bind(element as DigitModel)
            is EmptyViewHolder     -> holder.bind(element as EmptyModel)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = adapterDataList[position]
        return when (comparable) {
            is ParagraphModel -> TYPE_PARAGRAPH
            is ChapoModel     -> TYPE_CHAPO
            is EmbedModel     -> TYPE_EMBED
            is DigitModel     -> TYPE_DIGIT
            is EmptyModel     -> TYPE_EMPTY
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    override fun getItemCount(): Int = adapterDataList.size

    inner class ChapoViewHolder(itemView: View) : BaseViewHolder<ChapoModel>(itemView) {

        private val textView = itemView.findViewById<TextView>(R.id.chapo_content)

        override fun bind(item: ChapoModel) {
            textView.text = HtmlCompat.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }

    }

    inner class ParagraphViewHolder(itemView: View) : BaseViewHolder<ParagraphModel>(itemView) {

        private val textView = itemView.findViewById<TextView>(R.id.paragraph_content)

        override fun bind(item: ParagraphModel) {
            textView.text = HtmlCompat.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }

    }

    inner class EmbedViewHolder(itemView: View) : BaseViewHolder<EmbedModel>(itemView) {

        private val webView = itemView.findViewById<WebView>(R.id.embed_webview)

        override fun bind(item: EmbedModel) {
            webView.loadData(item.html, "text/html", "")
            webView.settings.setJavaScriptEnabled(true)
        }

    }

    inner class DigitViewHolder(itemView: View) : BaseViewHolder<DigitModel>(itemView) {

        private val titleView = itemView.findViewById<TextView>(R.id.digit_title)
        private val contentView = itemView.findViewById<TextView>(R.id.digit_content)

        override fun bind(item: DigitModel) {
            titleView.text = item.title
            contentView.text = HtmlCompat.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }

    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder<EmptyModel>(itemView) {

        override fun bind(item: EmptyModel) {
        }

    }
}