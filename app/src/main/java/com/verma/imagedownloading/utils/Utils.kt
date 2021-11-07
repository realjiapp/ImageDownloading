package com.verma.imagedownloading.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.verma.imagedownloading.R
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

fun Context.showSnackBarPermission(view: View, activity: Activity) {
    Snackbar.make(
        view, this.getString(R.string.enable_permission),
        Snackbar.LENGTH_LONG
    ).setAction(this.getString(R.string.enable)) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + activity.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        activity.startActivity(intent)
    }.setActionTextColor(Color.GRAY).show()
}
