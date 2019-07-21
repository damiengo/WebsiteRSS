package com.damiengo.websiterss.ui.home

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.damiengo.websiterss.article.MyArticle
import com.damiengo.websiterss.util.GlideApp

class ArticleAdapter(private val dataSource: MutableList<MyArticle>,
                     private val clickListener: (MyArticle) -> Unit) : RecyclerView.Adapter<ArticleViewHolder>(),
    ListPreloader.PreloadModelProvider<MyArticle> {

    private val imageSize: Int = 210

    override fun getPreloadItems(position: Int): MutableList<MyArticle> {
        return dataSource.subList(position, position+1)
    }

    override fun getPreloadRequestBuilder(myArticle: MyArticle): RequestBuilder<*>? {
        return GlideApp.with(context)
                       .load(myArticle.article.image)
                       .diskCacheStrategy(DiskCacheStrategy.ALL)
                       .override(imageSize, imageSize)
                       .centerCrop()
    }

    private lateinit var context: Context

    // Must be quick, on the main thread
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ArticleViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(context)
        return ArticleViewHolder(inflater, parent)
    }

    // Must be quick, on the main thread
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val myArticle: MyArticle = dataSource[position]

        GlideApp.with(context)
            .load(myArticle.article.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .override(imageSize, imageSize)
            .into(holder.getImageView())


        holder.bind(myArticle, clickListener)
    }

    override fun getItemCount(): Int = dataSource.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}