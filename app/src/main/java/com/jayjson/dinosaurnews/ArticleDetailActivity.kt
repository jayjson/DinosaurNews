package com.jayjson.dinosaurnews

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import com.jayjson.dinosaurnews.databinding.ActivityArticleDetailBinding
import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Failure
import com.jayjson.dinosaurnews.model.OperationState
import com.jayjson.dinosaurnews.model.Success
import com.jayjson.dinosaurnews.worker.DownloadImageWorker
import com.jayjson.dinosaurnews.worker.FileClearWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding
    private lateinit var titleTextView: TextView
    private lateinit var articleImageView: ImageView
    private lateinit var descriptionTextView: TextView
    private lateinit var bookmarkButton: Button

    lateinit var article: Article

    private val bookmarkManager: BookmarkManager = BookmarkManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindingAndViews()
        populateArticleDetails()
    }

    private fun setupBindingAndViews() {
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        titleTextView = binding.titleTextView
        articleImageView = binding.articleImageView
        descriptionTextView = binding.descriptionTextView

        bookmarkButton = binding.bookmarkButton
        bookmarkButton.setOnClickListener { view ->
            bookmarkManager.save(article)
            Snackbar.make(view, "Article Bookmarked!", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun populateArticleDetails() {
        val tempArticle = intent.getSerializableExtra(MainActivity.INTENT_ARTICLE_KEY) as? Article
        article = tempArticle!!
        if (article.urlToImage != null) {
            val url = article.urlToImage ?: ""
            downloadImage(url)
        } else {
            articleImageView.visibility = View.INVISIBLE
        }
        title = if (article.author != null) article.author else "Unknown author"
        titleTextView.text = article.title
        descriptionTextView.text = article.description
    }

    private fun downloadImage(imageUrlString: String) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
//            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()

        val clearFilesWorker = OneTimeWorkRequestBuilder<FileClearWorker>()
            .build()

        val downloadRequest = OneTimeWorkRequestBuilder<DownloadImageWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf(
                "IMAGE_URL" to imageUrlString
            ))
            .build()

        val workManager = WorkManager.getInstance(this)
        workManager.beginWith(clearFilesWorker)
            .then(downloadRequest)
            .enqueue()

        workManager.getWorkInfoByIdLiveData(downloadRequest.id).observe(this, Observer { info ->
            if (info.state.isFinished) {
                if (info.state == WorkInfo.State.SUCCEEDED) {
                    val imagePath = info.outputData.getString("image_path")
                    if (!imagePath.isNullOrEmpty()) {
                        displayImage(imagePath)
                    }
                } else {
                    val message = "Failed to fetch images!"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, message, duration)
                    toast.show()
                }
            }
        })
    }

    private fun displayImage(imagePath: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val bitmap = loadImageFromFile(imagePath)
            articleImageView.setImageBitmap(bitmap)
        }
    }

    private suspend fun loadImageFromFile(imagePath: String) = withContext(Dispatchers.IO) {
        BitmapFactory.decodeFile(imagePath)
    }
}