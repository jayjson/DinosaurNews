package com.jayjson.dinosaurnews.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Country
import com.jayjson.dinosaurnews.networking.RemoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.jayjson.dinosaurnews.model.Result
import com.jayjson.dinosaurnews.networking.NetworkStatusChecker
import com.jayjson.dinosaurnews.repository.NewsRepository
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.M)
class ArticlesListViewModel(private val newsRepo: NewsRepository): ViewModel() {

    class Factory(
        private val newsRepo: NewsRepository
        ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticlesListViewModel(newsRepo) as T
        }
    }

    private val _articles = MutableLiveData<Result<List<Article>>>()
    val articles: LiveData<Result<List<Article>>> = _articles

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "Fetching articles from API...")
            val fetchedArticles = newsRepo.getArticles()
            withContext(Dispatchers.Main) {
                _articles.postValue(fetchedArticles)
            }
        }
    }

    companion object {
        private const val TAG = "ArticlesListViewModel"
    }
}