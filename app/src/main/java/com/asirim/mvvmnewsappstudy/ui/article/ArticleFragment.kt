package com.asirim.mvvmnewsappstudy.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.asirim.mvvmnewsappstudy.R
import com.asirim.mvvmnewsappstudy.databinding.FragmentArticleBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel
import com.asirim.mvvmnewsappstudy.util.Constants.MY_GITHUB_LINK_FOR_NULL_ARTICLE_URL

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var newsViewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        binding.webViewArticle.webViewClient = WebViewClient()
        binding.webViewArticle.loadUrl(article.url ?: MY_GITHUB_LINK_FOR_NULL_ARTICLE_URL)

        binding.fabFavorite.setOnClickListener {
            newsViewModel.upsertNews(article)
            Toast.makeText(
                activity,
                getString(R.string.article_saved_successfully),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}