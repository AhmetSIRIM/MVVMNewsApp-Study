package com.asirim.mvvmnewsappstudy.ui.breakingnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asirim.mvvmnewsappstudy.R
import com.asirim.mvvmnewsappstudy.databinding.FragmentBreakingNewsBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel
import com.asirim.mvvmnewsappstudy.ui.adapter.ArticleAdapter
import com.asirim.mvvmnewsappstudy.util.Constants.QUERY_PAGE_SIZE
import com.asirim.mvvmnewsappstudy.util.Resource
import com.asirim.mvvmnewsappstudy.util.safeNavigate

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

        articleAdapter.setOnItemClickListener {
            findNavController().safeNavigate(
                BreakingNewsFragmentDirections
                    .actionBreakingNewsFragmentToArticleFragment(it)
            )
        }

        getNewsFromUsOrTrBySwitch()

        newsViewModel.breakingNews.observe(
            viewLifecycleOwner
        ) { breakingNewsResponse ->

            when (breakingNewsResponse) {

                is Resource.Error -> Toast.makeText(
                    activity,
                    breakingNewsResponse.message,
                    Toast.LENGTH_SHORT
                ).show()

                is Resource.Loading -> showProgressBar()

                is Resource.Success -> {
                    hideProgressBar()
                    breakingNewsResponse.data?.let {
                        articleAdapter.differ.submitList(it.articles?.toList())
                        val totalPages = (it.totalResults?.div(QUERY_PAGE_SIZE) ?: 10) + 2
                        isLastPage = newsViewModel.breakingNewsPage == totalPages
                        if (isLastPage) {
                            binding.recyclerViewBreakingNews.setPadding(0, 0, 0, 0)
                        }
                    }
                }

            }

        }

    }

    private fun hideProgressBar() {
        binding.progressBarPagination.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBarPagination.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate = isNotLoadingAndNotLastPage &&
                    isAtLastItem &&
                    isNotAtBeginning &&
                    isTotalMoreThanVisible &&
                    isScrolling

            if (shouldPaginate) {
                newsViewModel.getBreakingNews(US)
                isScrolling = false
            }

        }

    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.recyclerViewBreakingNews.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    private fun getNewsFromUsOrTrBySwitch() {
        binding.switchUsOrTr.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {

                true -> {
                    newsViewModel.getBreakingNews(TR)
                    binding.imageViewUsOrTr.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.ic_tr_flag
                        )
                    )
                }

                false -> {
                    newsViewModel.getBreakingNews(US)
                    setupRecyclerView()
                    binding.imageViewUsOrTr.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.ic_us_flag
                        )
                    )
                }

            }
        }
    }

    companion object {
        const val TR = "tr"
        const val US = "us"
    }

}