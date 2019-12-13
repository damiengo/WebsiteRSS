package com.damiengo.websiterss.util

import android.content.Context
import com.damiengo.websiterss.App
import com.damiengo.websiterss.category.Category
import com.damiengo.websiterss.category.CategoryHolder
import com.damiengo.websiterss.ui.home.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class AppModule {

    @Singleton
    @Binds
    @AppContext
    abstract fun provideContext(app: App): Context

    @ContributesAndroidInjector
    abstract fun mainActivityInjector() : MainActivity

    @ContributesAndroidInjector
    abstract fun categoryHolderInjector(): CategoryHolder

}