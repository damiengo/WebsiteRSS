package com.damiengo.websiterss.api

import com.damiengo.websiterss.article.json.ItemList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EquipeApi {

    @GET("/api/v1/efr/news/{article_id}")
    suspend fun getItems(@Path("article_id") articleId: String): Response<ItemList>

    @GET("/api/efr/comments/{category_slug}/{article_id}limits/{limits}/lasts/{lasts}")
    suspend fun getComments(@Path("category_slug") articleId: String,
                            @Path("article_id") article_id: String,
                            @Path("limits") limits: Int,
                            @Path("lasts") lasts: Int): Response<ItemList>

}