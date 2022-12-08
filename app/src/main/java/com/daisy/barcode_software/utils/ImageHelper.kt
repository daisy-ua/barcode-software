package com.daisy.barcode_software.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object Constants {
    const val FOLDER_NAME = "barcodes"
}

fun saveImage(context: Context, bitmap: Bitmap, name: String): Boolean {
    val saved: Boolean
    val fos: OutputStream?

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val resolver: ContentResolver = context.contentResolver
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
            "Pictures/${Constants.FOLDER_NAME}")
        val imageUri: Uri? =
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fos = imageUri?.let { resolver.openOutputStream(it) }
    } else {
        val imagesDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM).toString() + File.separator + Constants.FOLDER_NAME
        val file = File(imagesDir)
        if (!file.exists()) {
            file.mkdir()
        }
        val image = File(imagesDir, "$name.png")
        fos = FileOutputStream(image)
    }
    saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
    fos?.flush()
    fos?.close()

    return saved
}

fun convertUriToBitmap(context: Context, uri: Uri): Bitmap? {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } else {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(
            source
        ) { imageDecoder, _, _ ->
            imageDecoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            imageDecoder.isMutableRequired = true
        }
    }
}