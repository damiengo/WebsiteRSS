package com.damiengo.websiterss.util

import com.damiengo.websiterss.article.ArticleUtil
import com.damiengo.websiterss.category.Category
import com.damiengo.websiterss.category.CategoryHolder
import com.damiengo.websiterss.category.ClassCategoriesBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaggerModule {

    @Singleton
    @Provides
    internal fun provideCategories(): Map<Int, Category> {
        val categoriesBuilder = ClassCategoriesBuilder()
        return categoriesBuilder.build()
    }

    @Singleton
    @Provides
    internal fun provideCategoryHolder(): CategoryHolder {
        return CategoryHolder()
    }

    @Singleton
    @Provides
    internal fun provideArticleUtil(): ArticleUtil {
        return ArticleUtil()
    }

}