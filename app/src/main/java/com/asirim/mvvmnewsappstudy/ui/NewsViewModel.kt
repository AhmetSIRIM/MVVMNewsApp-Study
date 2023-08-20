package com.asirim.mvvmnewsappstudy.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.asirim.mvvmnewsappstudy.MVVMNewsAppStudyApp
import com.asirim.mvvmnewsappstudy.R
import com.asirim.mvvmnewsappstudy.data.dto.Article
import com.asirim.mvvmnewsappstudy.data.dto.NewsResponse
import com.asirim.mvvmnewsappstudy.data.repository.NewsRepository
import com.asirim.mvvmnewsappstudy.ui.breakingnews.BreakingNewsFragment.Companion.US
import com.asirim.mvvmnewsappstudy.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val mvvmNewsAppStudyApp: MVVMNewsAppStudyApp
) : AndroidViewModel(mvvmNewsAppStudyApp) {

    // 'AndroidViewModel is same as theViewModel but
    // inside of AndroidViewModel we can use the application context.'
    // Description of AndroidViewModel : Application context aware ViewModel.

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var breakingNewsResponse: NewsResponse? = null
    var breakingNewsPage = 1

    val searchedNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var searchedNewsPage = 1

    init {
        getBreakingNews(US)
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {

        breakingNews.postValue(Resource.Loading())

        try {

            if (hasInternetConnection()) {

                val safeBreakingNewsResponse = newsRepository
                    .getBreakingNews(countryCode, breakingNewsPage)

                breakingNews.postValue(
                    handleBreakingNewsResponse(
                        safeBreakingNewsResponse
                    )
                )

            } else {

                breakingNews.postValue(
                    Resource.Error(message = mvvmNewsAppStudyApp.getString(R.string.no_internet_connection))
                )

            }

        } catch (throwable: Throwable) {

            when (throwable) {

                is IOException -> breakingNews.postValue(
                    Resource.Error(
                        message = mvvmNewsAppStudyApp.getString(
                            R.string.unknown_error
                        )
                    )
                )

                else -> breakingNews.postValue(
                    Resource.Error(
                        message = mvvmNewsAppStudyApp.getString(
                            R.string.conversion_error
                        )
                    )
                )

            }

        }

    }

    private fun handleBreakingNewsResponse(newsResponse: Response<NewsResponse>): Resource<NewsResponse> {

        if (newsResponse.isSuccessful) {
            newsResponse.body()?.let { resultBreakingNewsResponse ->

                breakingNewsPage++

                when (breakingNewsResponse) {

                    null -> breakingNewsResponse = resultBreakingNewsResponse

                    else -> {

                        val oldArticles = breakingNewsResponse?.articles
                        val newArticles = resultBreakingNewsResponse.articles

                        oldArticles?.addAll(newArticles ?: oldArticles)

                    }

                }

                return Resource.Success(data = breakingNewsResponse ?: resultBreakingNewsResponse)

            }

        }

        return Resource.Error(message = newsResponse.message())

    }

    fun getSearchedNews(searchQuery: String) = viewModelScope.launch {

        searchedNews.postValue(Resource.Loading())

        try {

            if (hasInternetConnection()) {

                val safeSearchedNewsResponse = newsRepository
                    .getSearchedNews(searchQuery, searchedNewsPage)

                searchedNews.postValue(
                    handleSearchedNewsResponse(
                        safeSearchedNewsResponse
                    )
                )

            } else {

                searchedNews.postValue(
                    Resource.Error(message = mvvmNewsAppStudyApp.getString(R.string.no_internet_connection))
                )

            }

        } catch (throwable: Throwable) {

            when (throwable) {

                is IOException -> searchedNews.postValue(
                    Resource.Error(
                        message = mvvmNewsAppStudyApp.getString(
                            R.string.unknown_error
                        )
                    )
                )

                else -> searchedNews.postValue(
                    Resource.Error(
                        message = mvvmNewsAppStudyApp.getString(
                            R.string.conversion_error
                        )
                    )
                )

            }

        }

    }

    private fun handleSearchedNewsResponse(newsResponse: Response<NewsResponse>): Resource<NewsResponse> {

        if (newsResponse.isSuccessful) {
            newsResponse.body()?.let { resultResponse ->
                return Resource.Success(data = resultResponse)
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

    private fun hasInternetConnection(): Boolean {

        val connectivityManager = getApplication<MVVMNewsAppStudyApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }

}