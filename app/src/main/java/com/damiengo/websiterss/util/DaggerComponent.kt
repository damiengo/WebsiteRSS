package com.damiengo.websiterss.util

import com.damiengo.websiterss.category.CategoryHolder
import com.damiengo.websiterss.ui.home.FeedViewModel
import com.damiengo.websiterss.ui.home.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DaggerModule::class])
interface DaggerComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(categoryHolder: CategoryHolder)
    fun inject(viewModel: FeedViewModel)

}