package com.damiengo.websiterss.category

import com.damiengo.websiterss.util.DaggerDaggerComponent
import javax.inject.Inject

class CategoryHolder {

    @Inject
    lateinit var categories: Map<Int, Category>

    init {
        DaggerDaggerComponent.create().inject(this)
    }

    fun getCurrentTitle(itemId: Int): String {
        categories[itemId]?.let {
            return it.title
        }

        return ""
    }

    fun getCurrentUrl(itemId: Int): String {
        categories[itemId]?.let {
            return it.url
        }

        return ""
    }

    fun getDefaultUrl(): String {
        categories.values.toTypedArray().getOrNull(0)?.let {
            return it.url
        }

        return ""
    }

}