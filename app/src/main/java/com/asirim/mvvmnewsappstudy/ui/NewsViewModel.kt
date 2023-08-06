package com.asirim.mvvmnewsappstudy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asirim.mvvmnewsappstudy.data.dto.NewsResponse
import com.asirim.mvvmnewsappstudy.data.repository.NewsRepository
import com.asirim.mvvmnewsappstudy.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countreyCode: String) = viewModelScope.launch {

        breakingNews.postValue(Resource.Loading())

        val newsResponse = newsRepository.getBreakingNews(countreyCode, breakingNewsPage)

        breakingNews.postValue(handleBreakingNewsResponse(newsResponse))

    }

    private fun handleBreakingNewsResponse(newsResponse: Response<NewsResponse>): Resource<NewsResponse> {

        if (newsResponse.isSuccessful) {
            newsResponse.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(message = newsResponse.message())

    }

}