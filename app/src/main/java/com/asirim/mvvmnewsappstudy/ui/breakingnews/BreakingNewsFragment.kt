package com.asirim.mvvmnewsappstudy.ui.breakingnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.asirim.mvvmnewsappstudy.R
import com.asirim.mvvmnewsappstudy.databinding.FragmentBreakingNewsBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel
import com.asirim.mvvmnewsappstudy.ui.adapter.ArticleAdapter
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
                    R.string.unknown_error,
                    Toast.LENGTH_SHORT
                ).show()

                is Resource.Loading -> showProgressBar()

                is Resource.Success -> {
                    hideProgressBar()
                    breakingNewsResponse.data?.let { articleAdapter.differ.submitList(it.articles) }
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
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
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