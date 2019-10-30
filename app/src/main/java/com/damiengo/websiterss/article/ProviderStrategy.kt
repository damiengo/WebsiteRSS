package com.damiengo.websiterss.article

import com.damiengo.websiterss.ui.articledetail.model.Model

interface ProviderStrategy {

    suspend fun read(url: String): MutableList<Model>

}