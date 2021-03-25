package com.example.petproject2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.FileNotFoundException

class ImageLoader(private val activity: Activity) {
    private val RESULT_LOAD_IMAGE = 1

    fun runRequest() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        activity.startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE)
    }

    fun handleRequest(reqCode: Int, resultCode: Int, data: Intent): Bitmap? {
        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri: Uri? = data.data
                imageUri?.let { uri ->
                    activity.contentResolver.openInputStream(uri)?.let { stream ->
                        val selectedImage = BitmapFactory.decodeStream(stream)
                        return selectedImage
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return null
            }
        }
        return null
    }
}

