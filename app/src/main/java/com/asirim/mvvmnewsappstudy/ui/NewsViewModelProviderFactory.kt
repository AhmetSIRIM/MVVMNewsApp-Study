package com.asirim.mvvmnewsappstudy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asirim.mvvmnewsappstudy.MVVMNewsAppStudyApp
import com.asirim.mvvmnewsappstudy.data.repository.NewsRepository

@Suppress("UNCHECKED_CAST")
class NewsViewModelProviderFactory(
    private val newsRepository: NewsRepository,
    private val mvvmNewsAppStudyApp: MVVMNewsAppStudyApp
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository, mvvmNewsAppStudyApp) as T
    }

}