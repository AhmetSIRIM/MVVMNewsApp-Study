package com.asirim.mvvmnewsappstudy.data.repository

import com.asirim.mvvmnewsappstudy.data.api.RetrofitInstance
import com.asirim.mvvmnewsappstudy.data.dto.NewsResponse
import com.asirim.mvvmnewsappstudy.data.local.ArticleDatabase
import retrofit2.Response

class NewsRepository(
    val articleDatabase: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return RetrofitInstance.newsApi.getBreakingNews(countryCode, pageNumber)
    }

    suspend fun getSearchedNews(searchQuery: String, pageNumber: Int): Response<NewsResponse> {
        return RetrofitInstance.newsApi.getSearchedNews(searchQuery, pageNumber)
    }

}