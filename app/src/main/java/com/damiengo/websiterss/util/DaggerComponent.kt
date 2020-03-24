package com.damiengo.websiterss.util

import com.damiengo.websiterss.article.DomProviderStrategy
import com.damiengo.websiterss.article.JsonProviderStrategy
import com.damiengo.websiterss.article.json.ItemObject
import com.damiengo.websiterss.article.json.Landscape
import com.damiengo.websiterss.article.json.Paragraph
import com.damiengo.websiterss.category.CategoryHolder
import com.damiengo.websiterss.comment.JsonProvider
import com.damiengo.websiterss.ui.articledetail.ArticleDetailActivity
import com.damiengo.websiterss.ui.articledetail.ArticleDetailAdapter
import com.damiengo.websiterss.ui.home.FeedViewModel
import com.damiengo.websiterss.ui.home.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DaggerModule::class])
interface DaggerComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(articleDetailActivity: ArticleDetailActivity)
    fun inject(categoryHolder: CategoryHolder)
    fun inject(viewModel: FeedViewModel)
    fun inject(domProviderStrategy: DomProviderStrategy)
    fun inject(jsonProviderStrategy: JsonProviderStrategy)
    fun inject(jsonProvider: JsonProvider)
    fun inject(paragraph: Paragraph)
    fun inject(itemObject: ItemObject)
    fun inject(itemObject: Landscape)
    fun inject(articleDetailAdapter: ArticleDetailAdapter)
    fun inject(themeUtil: ThemeUtil)

}