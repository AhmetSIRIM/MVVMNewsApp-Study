package com.asirim.mvvmnewsappstudy.ui.breakingnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asirim.mvvmnewsappstudy.R
import com.asirim.mvvmnewsappstudy.databinding.FragmentBreakingNewsBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel
import com.asirim.mvvmnewsappstudy.ui.adapter.ArticleAdapter
import com.asirim.mvvmnewsappstudy.util.Resource

class BreakingNewsFragment : Fragment() {

    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel = (activity as NewsActivity).newsViewModel

        setupRecyclerView()

        newsViewModel.breakingNews.observe(
            viewLifecycleOwner
        ) { response ->
            when (response) {

                is Resource.Error -> Toast.makeText(
                    activity,
                    R.string.unknown_error,
                    Toast.LENGTH_SHORT
                ).show()

                is Resource.Loading -> showProgressBar()

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        articleAdapter.differ.submitList(newsResponse.articles)
                    }
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
        binding.recyclerViewBreakingNews.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}