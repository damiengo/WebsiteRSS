package com.damiengo.websiterss.util

import com.damiengo.websiterss.ui.home.FeedViewModel
import com.damiengo.websiterss.ui.home.MainActivity
import dagger.Component

@Component
interface MagicBox {
    fun inject(activity: MainActivity)
    fun inject(feedViewModel: FeedViewModel)
}