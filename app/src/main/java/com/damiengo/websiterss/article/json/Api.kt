package com.damiengo.websiterss.article.json

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("/{article_id}")
    fun getItems(@Path("article_id") articleId: String): Call<ItemList>

}