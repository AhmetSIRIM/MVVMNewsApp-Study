package com.asirim.mvvmnewsappstudy.ui.savednews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asirim.mvvmnewsappstudy.R
import com.asirim.mvvmnewsappstudy.databinding.FragmentSavedNewsBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel
import com.asirim.mvvmnewsappstudy.ui.adapter.ArticleAdapter
import com.asirim.mvvmnewsappstudy.util.safeNavigate
import com.google.android.material.snackbar.Snackbar

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
            findNavController().safeNavigate(
                SavedNewsFragmentDirections
                    .actionSavedNewsFragmentToArticleFragment(it)
            )
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
                // We don't use it in this app.
                // Explanation of 'onMove' -> https://youtu.be/hMpP6N9LGFA?t=405
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val articlePosition = viewHolder.adapterPosition
                val article = articleAdapter.differ.currentList[articlePosition]
                newsViewModel.deleteArticle(article)
                Snackbar.make(
                    view,
                    getString(R.string.article_deleted_successfully),
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    newsViewModel.upsertNews(article)
                }.show()

            }

        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewSavedNews)

        newsViewModel.getAllArticles().observe(viewLifecycleOwner) { savedArticles ->
            articleAdapter.differ.submitList(savedArticles)
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