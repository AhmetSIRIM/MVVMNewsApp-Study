package com.asirim.mvvmnewsappstudy.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.asirim.mvvmnewsappstudy.databinding.FragmentArticleBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var newsViewModel: NewsViewModel

    /* If you want to use serialization watch this: https://youtu.be/SlOTIcDQOqI */
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

        binding.webViewArticle.apply {
            webViewClient = WebViewClient()
            loadUrl(args.articleUrl)
        }

    }

}