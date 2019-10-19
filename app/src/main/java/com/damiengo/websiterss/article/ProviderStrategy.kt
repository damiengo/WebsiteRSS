package com.damiengo.websiterss.article

interface ProviderStrategy {

    suspend fun read(url: String)

    fun getChapo(): String

    fun getDescription(): String

}