package com.asirim.mvvmnewsappstudy.data.repository

import androidx.lifecycle.LiveData
import com.asirim.mvvmnewsappstudy.data.api.RetrofitInstance
import com.asirim.mvvmnewsappstudy.data.dto.Article
import com.asirim.mvvmnewsappstudy.data.dto.NewsResponse
import com.asirim.mvvmnewsappstudy.data.local.ArticleDao
import retrofit2.Response

class NewsRepository(
    private val articleDao: ArticleDao
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return RetrofitInstance.newsApi.getBreakingNews(countryCode, pageNumber)
    }

    suspend fun getSearchedNews(searchQuery: String, pageNumber: Int): Response<NewsResponse> {
        return RetrofitInstance.newsApi.getSearchedNews(searchQuery, pageNumber)
    }

    suspend fun upsertNews(article: Article) {
        articleDao.upsertArticle(article)
    }

    fun getAllArticles(): LiveData<List<Article>> {
        return articleDao.getAllArticles()
    }

    suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article)
    }

}