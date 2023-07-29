package com.asirim.mvvmnewsappstudy.ui

import androidx.lifecycle.ViewModel
import com.asirim.mvvmnewsappstudy.data.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel()