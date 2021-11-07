package com.verma.imagedownloading.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.verma.imagedownloading.data.model.ImageData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel() : ViewModel() {

    var mOnPublishProgress = MutableLiveData<ImageData>()
    var mOnDownloadComplete = MutableLiveData<ImageData>()

    var array = arrayListOf<Int>()

    fun downloadFile(
        imageNo: Int,
        url: String,
        filename: String,
        directoryPath: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            array.add(
                PRDownloader.download(
                    url, directoryPath, filename
                ).build()
                    .setOnStartOrResumeListener {
                        Log.d("PRDownloader", "Resume")
                    }
                    .setOnPauseListener {
                        Log.d("PRDownloader", "Paused")
                    }
                    .setOnCancelListener {

                    }
                    .setOnProgressListener {
                        val progressPercent: Long = it.currentBytes * 100 / it.totalBytes
                        Log.d("PRDownloader", "$progressPercent")
                        val imgData = ImageData(imageNo, progressPercent.toInt())
                        mOnPublishProgress.postValue(imgData)
                    }
                    .start(object : OnDownloadListener {
                        override fun onDownloadComplete() {
                            val imgFile = File("$directoryPath/$filename")
                            val imgData = ImageData(imageNo, 100, imgFile)
                            mOnDownloadComplete.postValue(imgData)
                        }

                        override fun onError(error: Error?) {
                        }
                    })
            )
        }
    }


    fun pause() {
        try {
            for (i in 0 until array.size) {
                PRDownloader.pause(array[i])
            }
        } catch (e: Exception) {
        }
    }

    fun resume() {
        try {
            for (i in 0 until array.size) {
                PRDownloader.resume(array[i])
            }
        } catch (e: Exception) {
        }
    }

}