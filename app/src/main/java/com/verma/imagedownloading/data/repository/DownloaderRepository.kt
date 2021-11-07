package com.verma.imagedownloading.data.repository

import android.util.Log
import android.widget.ProgressBar
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import java.io.File


class DownloaderRepository {
    var downloadIdsList = arrayListOf<Int>()



    fun pause() {
        for (i in 0 until downloadIdsList.size) {
            PRDownloader.pause(downloadIdsList[i])
        }
    }

    fun resume() {
        for (i in 0 until downloadIdsList.size) {
            PRDownloader.resume(downloadIdsList[i])
        }
    }


}