package com.jayjson.dinosaurnews

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Source
import com.jayjson.dinosaurnews.model.Success
import com.jayjson.dinosaurnews.prefsstore.PrefsStoreImp
import com.jayjson.dinosaurnews.repository.NewsRepositoryImp
import com.jayjson.dinosaurnews.viewmodel.ArticlesListViewModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class ArticlesListViewModelTest {

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

    @Test
    fun testGettingAndEmittingArticles() = runBlocking {
        val repo = mockk<NewsRepositoryImp>()
        val fakeResult = Success(listOf(fakeArticle))
        val prefsStore = mockk<PrefsStoreImp>()

        every { repo.getArticles() } returns flow {
            emit(fakeResult)
        }

        val viewModel = ArticlesListViewModel(repo, prefsStore)
        delay(100) // To make sure that the flow emit occurs before checking
        assertEquals(fakeResult, viewModel.articles.value)
    }
}