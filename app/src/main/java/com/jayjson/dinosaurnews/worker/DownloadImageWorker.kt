package com.jayjson.dinosaurnews.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadImageWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        val imageUrlString = inputData.getString("IMAGE_URL") ?: return Result.failure()
        val imageUrl = URL(imageUrlString)
        val connection = imageUrl.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val imagePath = "article_image_${System.currentTimeMillis()}.jpg"
        val inputStream = connection.inputStream
        val file = File(applicationContext.externalMediaDirs.first(), imagePath)

        val outputStream = FileOutputStream(file)
        outputStream.use { output ->
            val buffer = ByteArray(4 * 1024)

            var byteCount = inputStream.read(buffer)

            while (byteCount > 0) {
                output.write(buffer, 0, byteCount)

                byteCount = inputStream.read(buffer)
            }

            output.flush()
        }
        val output = workDataOf("image_path" to file.absolutePath)
        return Result.success(output)
    }
}