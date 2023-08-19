package com.asirim.mvvmnewsappstudy.ui.searchnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.asirim.mvvmnewsappstudy.R
import com.asirim.mvvmnewsappstudy.databinding.FragmentSearchNewsBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel
import com.asirim.mvvmnewsappstudy.ui.adapter.ArticleAdapter
import com.asirim.mvvmnewsappstudy.util.Constants.SEARCH_NEWS_TIME_DELAY
import com.asirim.mvvmnewsappstudy.util.Resource
import com.asirim.mvvmnewsappstudy.util.safeNavigate
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel = (activity as NewsActivity).newsViewModel

        setupRecyclerView()

        articleAdapter.setOnItemClickListener {
            findNavController().safeNavigate(
                SearchNewsFragmentDirections
                    .actionSearchNewsFragmentToArticleFragment(it)
            )
        }

        var job: Job? = null
        binding.editTextSearch.addTextChangedListener { editTextSearchEditable ->

            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)

                editTextSearchEditable?.let {
                    if (editTextSearchEditable.toString().isNotBlank()) {
                        newsViewModel.getSearchedNews(editTextSearchEditable.toString())
                    }
                }

            }

        }

        newsViewModel.searchedNews.observe(
            viewLifecycleOwner
        ) { searchedNewsResponse ->

            when (searchedNewsResponse) {

                is Resource.Error -> Toast.makeText(
                    activity,
                    R.string.unknown_error,
                    Toast.LENGTH_SHORT
                ).show()

                is Resource.Loading -> showProgressBar()

                is Resource.Success -> {
                    hideProgressBar()
                    searchedNewsResponse.data?.let { articleAdapter.differ.submitList(it.articles) }
                }

            }

        }

    }

    private fun hideProgressBar() {
        binding.progressBarPagination.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBarPagination.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.recyclerViewSearchedNews.apply {
            adapter = articleAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

}