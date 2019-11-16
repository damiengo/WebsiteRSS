package com.damiengo.websiterss.ui.articledetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.damiengo.websiterss.R
import com.damiengo.websiterss.article.ArticleUtil
import com.damiengo.websiterss.ui.articledetail.model.*
import com.damiengo.websiterss.util.DaggerDaggerComponent
import javax.inject.Inject

class ArticleDetailAdapter(private val context: Context): RecyclerView.Adapter<BaseViewHolder<*>>() {

    @Inject
    lateinit var util: ArticleUtil

    private var adapterDataList = mutableListOf<Model>()

    companion object {
        private const val TYPE_PARAGRAPH = 0
        private const val TYPE_CHAPO     = 1
        private const val TYPE_EMPTY     = 2
        private const val TYPE_EMBED     = 3
        private const val TYPE_DIGIT     = 4
        private const val TYPE_CITATION  = 5
        private const val TYPE_NOTE      = 6
        private const val TYPE_FOCUS     = 7
        private const val TYPE_TWITTER   = 8
    }

    init {
        DaggerDaggerComponent.create().inject(this)
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
            TYPE_CITATION -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_citation_item, parent, false)
                CitationViewHolder(view)
            }
            TYPE_NOTE -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_note_item, parent, false)
                NoteViewHolder(view)
            }
            TYPE_FOCUS -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_focus_item, parent, false)
                FocusViewHolder(view)
            }
            TYPE_TWITTER -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_twitter_item, parent, false)
                TwitterViewHolder(view)
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
            is CitationViewHolder  -> holder.bind(element as CitationModel)
            is NoteViewHolder      -> holder.bind(element as NoteModel)
            is FocusViewHolder     -> holder.bind(element as FocusModel)
            is TwitterViewHolder   -> holder.bind(element as TwitterModel)
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
            is CitationModel  -> TYPE_CITATION
            is NoteModel      -> TYPE_NOTE
            is FocusModel     -> TYPE_FOCUS
            is TwitterModel   -> TYPE_TWITTER
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
            val textWithoutLinks = util.removeLinksFromText(item.content)
            textView.text = HtmlCompat.fromHtml(textWithoutLinks, HtmlCompat.FROM_HTML_MODE_COMPACT)
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

    inner class CitationViewHolder(itemView: View) : BaseViewHolder<CitationModel>(itemView) {

        private val contentView = itemView.findViewById<TextView>(R.id.citation_content)
        private val captionView = itemView.findViewById<TextView>(R.id.citation_caption)

        override fun bind(item: CitationModel) {
            contentView.text = HtmlCompat.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
            captionView.text = HtmlCompat.fromHtml(item.caption, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }

    }

    inner class NoteViewHolder(itemView: View) : BaseViewHolder<NoteModel>(itemView) {

        private val playerView = itemView.findViewById<TextView>(R.id.note_player)
        private val contentView = itemView.findViewById<TextView>(R.id.note_content)

        override fun bind(item: NoteModel) {
            playerView.text = HtmlCompat.fromHtml(item.player+": "+item.rating, HtmlCompat.FROM_HTML_MODE_COMPACT)
            contentView.text = HtmlCompat.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }

    }

    inner class FocusViewHolder(itemView: View) : BaseViewHolder<FocusModel>(itemView) {

        private val contentView = itemView.findViewById<TextView>(R.id.focus_content)

        override fun bind(item: FocusModel) {
            contentView.text = HtmlCompat.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }

    }

    inner class TwitterViewHolder(itemView: View) : BaseViewHolder<TwitterModel>(itemView) {

        private val contentView = itemView.findViewById<TextView>(R.id.twitter_content)
        private val authorView = itemView.findViewById<TextView>(R.id.twitter_author)
        private val buttonView = itemView.findViewById<Button>(R.id.twitter_button)

        override fun bind(item: TwitterModel) {
            contentView.text = HtmlCompat.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
            authorView.text = HtmlCompat.fromHtml(item.author, HtmlCompat.FROM_HTML_MODE_COMPACT)

            buttonView.setOnClickListener {
                val uri = Uri.parse(item.link)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = uri
                startActivity(context, intent, null)
            }
        }

    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder<EmptyModel>(itemView) {

        override fun bind(item: EmptyModel) {
        }

    }
}