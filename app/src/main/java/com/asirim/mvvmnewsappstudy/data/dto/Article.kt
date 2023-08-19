package com.asirim.mvvmnewsappstudy.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("articles_table")
data class Article(
    @PrimaryKey(true)
    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable