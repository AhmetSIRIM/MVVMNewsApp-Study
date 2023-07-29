package com.asirim.mvvmnewsappstudy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asirim.mvvmnewsappstudy.data.dto.Article
import com.asirim.mvvmnewsappstudy.databinding.ItemArticleBinding
import com.bumptech.glide.Glide

class ArticleAdapter : RecyclerView.Adapter<ArticleViewHolder>() {

    private lateinit var binding: ItemArticleBinding

    private var onItemClickListener: ((Article) -> Unit)? = null

    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = differ.currentList[position]

        holder.itemView.apply {

            Glide.with(this).load(article.urlToImage).into(binding.imageViewArticleImage)

            binding.apply {
                textViewSource.text = article.source.toString()
                textViewTitle.text = article.title.toString()
                textViewDescription.text = article.description.toString()
                textViewPublishedAt.text = article.publishedAt.toString()
            }

            onItemClickListener?.let {
                it(article)
            }

        }

    }

    override fun getItemCount() = differ.currentList.size

    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }


}