package com.asirim.mvvmnewsappstudy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asirim.mvvmnewsappstudy.data.dto.Article
import com.asirim.mvvmnewsappstudy.databinding.ItemArticleBinding
import com.asirim.mvvmnewsappstudy.util.formatStringTime
import com.bumptech.glide.Glide

class ArticleAdapter : RecyclerView.Adapter<ArticleViewHolder>() {

    private lateinit var binding: ItemArticleBinding
    private var onItemClickListener: ((String?) -> Unit)? = null

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ArticleViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = differ.currentList[position]

        holder.itemView.apply {

            Glide.with(this).load(article.urlToImage).into(binding.imageViewArticleImage)

            binding.apply {
                textViewSource.append("\n${article.source?.name.toString()}")
                textViewTitle.text = article.title.toString()
                textViewDescription.text = article.description.toString()
                textViewPublishedAt.append("\n${article.publishedAt.toString().formatStringTime()}")
            }

        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(article.url)
            }
        }

    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (String?) -> Unit) {
        onItemClickListener = listener
    }

}