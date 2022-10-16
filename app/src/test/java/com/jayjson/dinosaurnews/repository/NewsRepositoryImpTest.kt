package com.jayjson.dinosaurnews.repository

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jayjson.dinosaurnews.database.dao.ArticleDao
import com.jayjson.dinosaurnews.database.dao.SourceDao
import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Result
import com.jayjson.dinosaurnews.model.Source
import com.jayjson.dinosaurnews.model.Success
import com.jayjson.dinosaurnews.networking.NetworkStatusChecker
import com.jayjson.dinosaurnews.networking.RemoteApi
import com.jayjson.dinosaurnews.prefsstore.PrefsStore
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class NewsRepositoryImpTest {

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

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun excludeLogFunctions() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @Test
    fun testGetArticles() = runBlocking {
        // given
        excludeLogFunctions()
        val fakeArticleDao = mockk<ArticleDao>()
        val fakeList = listOf(fakeArticle)
        every { fakeArticleDao.getArticles() } returns fakeList
        val repo = NewsRepositoryImp(
            articleDao = fakeArticleDao,
            sourceDao = mockk<SourceDao>(),
            remoteApi = mockk<RemoteApi>(),
            networkStatusChecker = mockk<NetworkStatusChecker>(),
            prefsStore = mockk<PrefsStore>()
        )

        // when
        var results = mutableListOf<Result<List<Article>>>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            repo.getArticles().toList(results)
        }

        // then
        assertEquals(Success(fakeList), results[0])

        collectJob.cancel()
    }

    @Test
    fun testAddArticles() {
        // given
        val fakeArticleDao = spyk<ArticleDao>()
        val fakeList = listOf(fakeArticle)
        val repo = NewsRepositoryImp(
            articleDao = fakeArticleDao,
            sourceDao = mockk<SourceDao>(),
            remoteApi = mockk<RemoteApi>(),
            networkStatusChecker = mockk<NetworkStatusChecker>(),
            prefsStore = mockk<PrefsStore>()
        )

        // when
        repo.addArticles(fakeList)

        // then
        verify { fakeArticleDao.addArticles(any()) }
    }

    @Test
    fun testClearArticles() {
        // given
        val fakeArticleDao = spyk<ArticleDao>()
        val repo = NewsRepositoryImp(
            articleDao = fakeArticleDao,
            sourceDao = mockk<SourceDao>(),
            remoteApi = mockk<RemoteApi>(),
            networkStatusChecker = mockk<NetworkStatusChecker>(),
            prefsStore = mockk<PrefsStore>()
        )

        // when
        repo.clearArticles()

        // then
        verify { fakeArticleDao.clearArticles() }
    }

    @Test
    fun testSearchArticles() {
        // given
        val fakeArticleDao = spyk<ArticleDao>()
        val repo = NewsRepositoryImp(
            articleDao = fakeArticleDao,
            sourceDao = mockk<SourceDao>(),
            remoteApi = mockk<RemoteApi>(),
            networkStatusChecker = mockk<NetworkStatusChecker>(),
            prefsStore = mockk<PrefsStore>()
        )

        // when
        repo.searchArticles("hello")

        // then
        verify { fakeArticleDao.searchArticles(any()) }
    }

    @Test
    fun testGetSources() {
        // given
        val fakeSourceDao = spyk<SourceDao>()
        val repo = NewsRepositoryImp(
            articleDao = mockk<ArticleDao>(),
            sourceDao = fakeSourceDao,
            remoteApi = mockk<RemoteApi>(),
            networkStatusChecker = mockk<NetworkStatusChecker>(),
            prefsStore = mockk<PrefsStore>()
        )

        // when
        repo.getSources()

        // then
        verify { fakeSourceDao.getSources() }
    }

    @Test
    fun testAddSources() {
        // given
        val fakeSourceDao = spyk<SourceDao>()
        val fakeSourceList = listOf(fakeSource)
        val repo = NewsRepositoryImp(
            articleDao = mockk<ArticleDao>(),
            sourceDao = fakeSourceDao,
            remoteApi = mockk<RemoteApi>(),
            networkStatusChecker = mockk<NetworkStatusChecker>(),
            prefsStore = mockk<PrefsStore>()
        )

        // when
        repo.addSources(fakeSourceList)

        // then
        verify { fakeSourceDao.addSources(any()) }
    }
}