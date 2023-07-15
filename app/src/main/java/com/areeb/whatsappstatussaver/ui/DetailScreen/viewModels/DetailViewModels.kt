package com.areeb.whatsappstatussaver.ui.DetailScreen.viewModels

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModels @Inject constructor(private val context: Context) : ViewModel() {

    suspend fun saveImage(bitmap: Bitmap, requireActivity: Activity) {
        val contentResolver: ContentResolver = requireActivity.applicationContext.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "image.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }

        var imageUri: String? = null

        try {
            val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val imageUri = contentResolver.insert(imageCollection, contentValues)
            if (imageUri != null) {
                contentResolver.openOutputStream(imageUri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }

                Toast.makeText(
                    context,
                    "Image saved successfully.",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Failed to save the image.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            imageUri?.let { uri ->
                contentResolver.delete(Uri.parse(uri), null, null)
            }
        }
    }
}
