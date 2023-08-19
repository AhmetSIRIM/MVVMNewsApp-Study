package com.asirim.mvvmnewsappstudy.data.dto

data class NewsResponse(
    val articles: MutableList<Article?>?,
    val status: String?,
    val totalResults: Int?
)