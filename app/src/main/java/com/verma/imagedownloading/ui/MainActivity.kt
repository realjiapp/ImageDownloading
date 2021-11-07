package com.verma.imagedownloading.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.verma.imagedownloading.R
import com.verma.imagedownloading.utils.getRootDirPath
import com.verma.imagedownloading.utils.setImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    private var dirPath: String? = null

    val filename = "WCkZue.png"
    val filename2 = "WCkZue1.jpg"
    val filename3 = "WCkZue2.jpg"
    val filename4 = "WCkZue3.png"

    val url = "https://cdn.wallpapersafari.com/36/6/WCkZue.png"
    val url2 =
        "https://www.iliketowastemytime.com/sites/default/files/hamburg-germany-nicolas-kamp-hd-wallpaper_0.jpg"
    val url3 =
        "https://images.hdqwalls.com/download/drift-transformers-5-the-last-knight-qu-5120x2880.jpg"
    val url4 =
        "https://survarium.com/sites/default/files/calendars/survarium-wallpaper-calendar-february-2016-en-2560x1440.png"

    var isAsync = false
    var imageCompleteCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dirPath = getRootDirPath(applicationContext)

        switchSync.setOnCheckedChangeListener { buttonView, isChecked ->
            isAsync = isChecked
        }

        viewModel.mOnPublishProgress.observe(this, {
            when (it.imageId) {
                1 -> {
                    progresBarOne.progress = it.progress
                }
                2 -> {
                    progresBarTwo.progress = it.progress
                }
                3 -> {
                    progresBarThree.progress = it.progress
                }
                4 -> {
                    progresBarFour.progress = it.progress
                }
            }
        })


        viewModel.mOnDownloadComplete.observe(this, {
            when (it.imageId) {
                1 -> {
                    imageCompleteCount = imageCompleteCount + 1
                    verifyComplete()
                    imgOne.setImage(it.file!!)
                    progresBarOne.visibility = View.GONE
                    if (!isAsync) {
                        lifecycleScope.launch {
                            viewModel.downloadFile(2, url2, filename2, dirPath!!)
                        }
                    }
                }
                2 -> {
                    imageCompleteCount = imageCompleteCount + 1
                    verifyComplete()
                    imgTwo.setImage(it.file!!)
                    progresBarTwo.visibility = View.GONE
                    if (!isAsync) {
                        lifecycleScope.launch {
                            viewModel.downloadFile(3, url2, filename2, dirPath!!)
                        }
                    }
                }
                3 -> {
                    imageCompleteCount = imageCompleteCount + 1
                    verifyComplete()
                    imgThree.setImage(it.file!!)
                    progresBarThree.visibility = View.GONE
                    if (!isAsync) {
                        lifecycleScope.launch {
                            viewModel.downloadFile(4, url3, filename3, dirPath!!)
                        }
                    }
                }
                4 -> {
                    imageCompleteCount = imageCompleteCount + 1
                    verifyComplete()
                    imgFour.setImage(it.file!!)
                    progresBarFour.visibility = View.GONE
                }
            }
        })

        btnStart.setOnClickListener {
            if (btnStart.text.equals(getString(R.string.pause))) {
                btnStart.text = getString(R.string.resume)
                viewModel.pause()
            } else if (btnStart.text.equals(getString(R.string.resume))) {

                viewModel.resume()
            } else if (btnStart.text.equals(getString(R.string.start_downloading))) {
                btnStart.text = getString(R.string.pause)

                if (!isAsync) {
                    lifecycleScope.launch {
                        viewModel.downloadFile(1, url, filename, dirPath!!)
                    }
                } else {
                    lifecycleScope.async {
                        viewModel.downloadFile(1, url, filename, dirPath!!)
                        viewModel.downloadFile(2, url2, filename2, dirPath!!)
                        viewModel.downloadFile(3, url3, filename3, dirPath!!)
                        viewModel.downloadFile(4, url4, filename4, dirPath!!)
                    }
                }
            }
        }

    }

    private fun verifyComplete() {
        if (imageCompleteCount >= 4) {
            btnStart.text = getString(R.string.start_downloading)
            btnStart.isEnabled = false
        }
    }


}