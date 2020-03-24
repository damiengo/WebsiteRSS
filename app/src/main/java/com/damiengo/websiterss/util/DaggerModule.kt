package com.damiengo.websiterss.util

import android.content.Context
import com.damiengo.websiterss.article.*
import com.damiengo.websiterss.article.EquipeApi
import com.damiengo.websiterss.category.Category
import com.damiengo.websiterss.category.CategoryHolder
import com.damiengo.websiterss.category.ClassCategoriesBuilder
import com.damiengo.websiterss.ui.articledetail.model.ModelFactory
import com.damiengo.websiterss.ui.home.NetworkInformation
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Singleton
    @Provides
    internal fun provideNetworkInformation(): NetworkInformation {
        return NetworkInformation()
    }

    @Singleton
    @Provides
    // SO says MutableList is better: https://stackoverflow.com/a/45461344
    internal fun provideProvidersStrategy(): MutableList<ProviderStrategy> {
        val providers = mutableListOf<ProviderStrategy>()
        providers.add(JsonProviderStrategy())
        providers.add(DomProviderStrategy())

        return providers
    }

    @Singleton
    @Provides
    internal fun provideArticleReader(): ArticleReader {
        return JsoupArticleReader()
    }

    @Singleton
    @Provides
    internal fun provideApi(): EquipeApi {
        val jsonApi = "https://dwh.lequipe.fr"

        val retrofit = Retrofit.Builder()
            .baseUrl(jsonApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
        return retrofit.create(EquipeApi::class.java)
    }

    @Singleton
    @Provides
    internal fun provideModelFactory(): ModelFactory {
        return ModelFactory()
    }

    @Singleton
    @Provides
    internal fun provideThemeUtil(@AppContext context: Context): ThemeUtil {
        return ThemeUtil(context)
    }

}