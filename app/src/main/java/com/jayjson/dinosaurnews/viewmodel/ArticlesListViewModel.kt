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
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.M)
class ArticlesListViewModel(private val remoteApi: RemoteApi, private val networkChecker: NetworkStatusChecker): ViewModel() {

    class Factory(
        private val remoteApi: RemoteApi,
        private val networkChecker: NetworkStatusChecker
        ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticlesListViewModel(remoteApi, networkChecker) as T
        }
    }

    private val _articles = MutableLiveData<Result<List<Article>>>()
    val articles: LiveData<Result<List<Article>>> = _articles

    init {
        networkChecker.performIfConnectedToInternet {
            viewModelScope.launch(Dispatchers.IO) {
                Log.i(TAG, "Fetching articles from API...")
                withContext(Dispatchers.Main) {
                    _articles.postValue(remoteApi.getTopHeadlines(Country.US))
                }
            }
        }
    }

    companion object {
        private const val TAG = "ArticlesListViewModel"
    }
}