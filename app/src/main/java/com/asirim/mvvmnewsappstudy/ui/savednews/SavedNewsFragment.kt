package com.asirim.mvvmnewsappstudy.ui.savednews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asirim.mvvmnewsappstudy.databinding.FragmentSavedNewsBinding
import com.asirim.mvvmnewsappstudy.ui.NewsActivity
import com.asirim.mvvmnewsappstudy.ui.NewsViewModel

class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding
    lateinit var newsViewModel: NewsViewModel

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
    }

}