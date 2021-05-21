package com.example.petproject2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.FileNotFoundException

class ImageLoader(private val activity: Activity) {
    private val RESULT_LOAD_IMAGE = 1
    private var imageUri: Uri? = null

    fun runRequest() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        activity.startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE)
    }

    fun handleRequest(reqCode: Int, resultCode: Int, data: Intent): Bitmap? {
        if (resultCode == Activity.RESULT_OK) {
            try {
                imageUri = data.data
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

    fun getImageUriString() : String? {
        var returnvalue : String? = null
        imageUri?.let {
            returnvalue = it.toString()
        }
        return returnvalue
    }

    fun convertStringUriToBitmap(strUri: String) : Bitmap? {
        try {
            val lImageUri = Uri.parse(strUri)
            lImageUri?.let { uri ->
                activity.contentResolver.openInputStream(uri)?.let { stream ->
                    val selectedImage = BitmapFactory.decodeStream(stream)
                    return selectedImage
                }
            } ?: run {
                return null
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }
    }
}

