package com.verma.imagedownloading.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment
import android.widget.ImageView
import androidx.core.content.ContextCompat
import java.io.File
import java.util.*

fun getRootDirPath(context: Context): String {
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val file = ContextCompat.getExternalFilesDirs(
            context.applicationContext,
            null
        )[0]
        file.absolutePath
    } else {
        context.applicationContext.filesDir.absolutePath
    }
}

fun getProgressDisplayLine(currentBytes: Long, totalBytes: Long): String {
    return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes)
}

private fun getBytesToMBString(bytes: Long): String {
    return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00))
}

fun ImageView.setImage(path: File) {
    val myBitmap = BitmapFactory.decodeFile(path.absolutePath)
    this.setImageBitmap(myBitmap)
}
fun ImageView.setImage(path: String) {
    val myBitmap = BitmapFactory.decodeFile(File(path).absolutePath)
    this.setImageBitmap(myBitmap)
}