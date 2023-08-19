package com.asirim.mvvmnewsappstudy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asirim.mvvmnewsappstudy.data.dto.Article
import com.asirim.mvvmnewsappstudy.data.dto.NewsResponse
import com.asirim.mvvmnewsappstudy.data.repository.NewsRepository
import com.asirim.mvvmnewsappstudy.ui.breakingnews.BreakingNewsFragment.Companion.US
import com.asirim.mvvmnewsappstudy.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchedNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getBreakingNews(US)
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {

        breakingNews.postValue(Resource.Loading())

        val breakingNewsResponse = newsRepository.getBreakingNews(countryCode, breakingNewsPage)

        breakingNews.postValue(handleBreakingNewsResponse(breakingNewsResponse))

    }

    private fun handleBreakingNewsResponse(newsResponse: Response<NewsResponse>): Resource<NewsResponse> {

        if (newsResponse.isSuccessful) {
            newsResponse.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(message = newsResponse.message())

    }

    fun getSearchedNews(searchQuery: String) = viewModelScope.launch {

        searchedNews.postValue(Resource.Loading())

        val searchedNewsResponse = newsRepository.getSearchedNews(searchQuery, searchNewsPage)

        searchedNews.postValue(handleSearchedNewsResponse(searchedNewsResponse))

    }

    private fun handleSearchedNewsResponse(newsResponse: Response<NewsResponse>): Resource<NewsResponse> {

        if (newsResponse.isSuccessful) {
            newsResponse.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(message = newsResponse.message())

    }

    fun upsertNews(article: Article) {
        viewModelScope.launch {
            newsRepository.upsertNews(article)
        }
    }

    fun getAllArticles() = newsRepository.getAllArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}