package com.asirim.mvvmnewsappstudy.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.asirim.mvvmnewsappstudy.data.dto.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converter::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {

        @Volatile
        private var articleDatabase: ArticleDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = articleDatabase ?: synchronized(LOCK) {
            articleDatabase ?: createDatabase(context).also {
                articleDatabase = it
            }
        }

        private fun createDatabase(context: Context): ArticleDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_database"
            ).build()
        }

    }

}