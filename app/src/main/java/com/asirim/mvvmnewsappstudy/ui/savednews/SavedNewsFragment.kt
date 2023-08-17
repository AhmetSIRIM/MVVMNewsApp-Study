package com.asirim.mvvmnewsappstudy.ui.savednews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.asirim.mvvmnewsappstudy.databinding.FragmentSavedNewsBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel
import com.asirim.mvvmnewsappstudy.ui.adapter.ArticleAdapter
import com.asirim.mvvmnewsappstudy.util.Constants

class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel = (activity as NewsActivity).newsViewModel

        setupRecyclerView()

        articleAdapter.setOnItemClickListener {

            findNavController().navigate(
                SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(
                    it ?: Constants.MY_GITHUB_LINK_FOR_NULL_ARTICLE_URL
                )
            )

        }

    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.recyclerViewSavedNews.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}