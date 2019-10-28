package com.damiengo.websiterss.util

import com.damiengo.websiterss.article.*
import com.damiengo.websiterss.article.json.Api
import com.damiengo.websiterss.category.Category
import com.damiengo.websiterss.category.CategoryHolder
import com.damiengo.websiterss.category.ClassCategoriesBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.damiengo.websiterss.ui.home.NetworkInformation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    @Singleton
    @Provides
    internal fun provideNetworkInformation(): NetworkInformation {
        return NetworkInformation()
    }

    @Singleton
    @Provides
    internal fun provideDomProviderStrategy(): DomProviderStrategy {
        return DomProviderStrategy()
    }

    @Singleton
    @Provides
    internal fun provideArticleReader(): ArticleReader {
        return JsoupArticleReader()
    }

    @Singleton
    @Provides
    internal fun provideApi(): Api {
        val jsonApi = "https://dwh.lequipe.fr/api/v1/"

        val retrofit = Retrofit.Builder()
            .baseUrl(jsonApi)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(Api::class.java!!)
    }

}