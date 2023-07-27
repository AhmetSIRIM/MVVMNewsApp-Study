package com.asirim.mvvmnewsappstudy.data.api

import com.asirim.mvvmnewsappstudy.BuildConfig.NEWS_API_KEY
import com.asirim.mvvmnewsappstudy.data.dto.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "tr",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = NEWS_API_KEY
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String = "tr",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = NEWS_API_KEY
    ): Response<NewsResponse>

}

