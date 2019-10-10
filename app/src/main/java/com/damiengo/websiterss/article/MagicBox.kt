package com.damiengo.websiterss.article

import com.damiengo.websiterss.ui.home.FeedViewModel
import dagger.Component

@Component
interface MagicBox {
    fun inject(mviewModel: FeedViewModel)
}