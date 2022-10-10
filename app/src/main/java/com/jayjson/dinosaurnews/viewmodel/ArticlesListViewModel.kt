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
import com.jayjson.dinosaurnews.model.Success
import com.jayjson.dinosaurnews.networking.NetworkStatusChecker
import com.jayjson.dinosaurnews.repository.NewsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
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
        viewModelScope.launch(IO) {
            newsRepo
                .getArticles()
                .onEach { newArticles ->
                    _articles.postValue(newArticles)
                }
                .collect()
        }
    }

    fun searchPlanets(search: String) {
        viewModelScope.launch(IO) {
            val filteredArticles = newsRepo.searchArticles("%$search%")
            _articles.postValue(Success(filteredArticles))
        }
    }

    companion object {
        private const val TAG = "ArticlesListViewModel"
    }
}