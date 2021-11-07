package com.verma.imagedownloading.data.model

import java.io.File

data class ImageData(
    var imageId: Int,
    var progress: Int,
    var file: File? =null)
