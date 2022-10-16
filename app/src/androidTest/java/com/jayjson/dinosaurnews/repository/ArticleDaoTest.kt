package com.jayjson.dinosaurnews.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jayjson.dinosaurnews.database.NewsDatabase
import com.jayjson.dinosaurnews.database.dao.ArticleDao
import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Source
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    companion object {
        val fakeSource = Source(name = "WSJ")
        val fakeArticle = Article(
            source = fakeSource,
            title = "Inflation is too high",
            description = "Inflation rate is still too high after several interest rate increases",
            url = "http://wsj.com/inflation",
            publishedAt = LocalDate.now().toString(),
            content = null
        )
    }

    private lateinit var newsDatabase: NewsDatabase
    private lateinit var articleDao: ArticleDao

    @Before
    fun initDb() {
        newsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDatabase::class.java
        ).build()
        articleDao = newsDatabase.articleDao()
    }

    @After
    fun closeDb() {
        newsDatabase.close()
    }

    @Test
    fun getArticlesReturnsEmptyList() {
        // when
        val result = articleDao.getArticles()

        // then
        Assert.assertEquals(emptyList<Article>(), result)
    }

    @Test
    fun testAddArticles() {
        // given
        val fakeList = listOf(fakeArticle)

        // when
        articleDao.addArticles(fakeList)

        // then
        val result = articleDao.getArticles()
        assertEquals(fakeList, result)
    }

    @Test
    fun testClearArticles() {
        // given
        val fakeList = listOf(fakeArticle)
        articleDao.addArticles(fakeList)

        // when
        articleDao.clearArticles()

        // then
        val result = articleDao.getArticles()
        assertEquals(emptyList<Article>(), result)
    }

    @Test
    fun testSearchArticles() {
        // given
        val fakeList = listOf(fakeArticle)
        articleDao.addArticles(fakeList)

        // when
        val result1 = articleDao.searchArticles(fakeArticle.title)
        val result2 = articleDao.searchArticles("zzz")

        // then
        assertEquals(fakeList, result1)
        assertEquals(emptyList<Article>(), result2)
    }
}