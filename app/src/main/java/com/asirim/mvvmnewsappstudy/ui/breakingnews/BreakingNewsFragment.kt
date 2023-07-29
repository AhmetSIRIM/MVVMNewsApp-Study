package com.asirim.mvvmnewsappstudy.ui.breakingnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asirim.mvvmnewsappstudy.databinding.FragmentBreakingNewsBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel

class BreakingNewsFragment : Fragment() {

    private var fragmentBreakingNewsBinding: FragmentBreakingNewsBinding? = null
    lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        fragmentBreakingNewsBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewsActivity).newsViewModel
    }

    override fun onDestroyView() {
        fragmentBreakingNewsBinding = null
        super.onDestroyView()
    }

}