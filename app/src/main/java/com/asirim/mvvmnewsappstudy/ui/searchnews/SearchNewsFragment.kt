package com.asirim.mvvmnewsappstudy.ui.searchnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asirim.mvvmnewsappstudy.databinding.FragmentSearchNewsBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel

class SearchNewsFragment : Fragment() {

    private var fragmentSearchNewsBinding: FragmentSearchNewsBinding? = null
    lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        fragmentSearchNewsBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewsActivity).newsViewModel
    }

    override fun onDestroyView() {
        fragmentSearchNewsBinding = null
        super.onDestroyView()
    }

}