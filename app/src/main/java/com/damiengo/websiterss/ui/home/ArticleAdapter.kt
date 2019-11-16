package com.damiengo.websiterss.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.damiengo.websiterss.article.MyArticle
import com.damiengo.websiterss.util.GlideApp

class ArticleAdapter(private val dataSource: MutableList<MyArticle>,
                     private val clickListener: (MyArticle) -> Unit) : RecyclerView.Adapter<ArticleViewHolder>(),
    ListPreloader.PreloadModelProvider<MyArticle> {

    private lateinit var context: Context


    override fun getPreloadItems(position: Int): MutableList<MyArticle> {
        return dataSource.subList(position, position+1)
    }

    override fun getPreloadRequestBuilder(myArticle: MyArticle): RequestBuilder<*>? {
        return GlideApp.with(context)
                       .load(myArticle.article.image)
                       .diskCacheStrategy(DiskCacheStrategy.ALL)
                       .override(IMAGE_SIZE, IMAGE_SIZE)
                       .centerCrop()
    }

    // Must be quick, on the main thread
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ArticleViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(context)
        return ArticleViewHolder(inflater, parent)
    }

    // Must be quick, on the main thread
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val myArticle: MyArticle = dataSource[position]

        GlideApp.with(context)
            .load(myArticle.article.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .override(IMAGE_SIZE, IMAGE_SIZE)
            .into(holder.getImageView())

        holder.bind(myArticle, clickListener)
    }

    override fun getItemCount(): Int = dataSource.size

    override fun getItemId(position: Int): Long {
        return dataSource[position].hashCode().toLong()
    }

}