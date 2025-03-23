package com.sdjic.gradnet.di.platform_di

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.sdjic.gradnet.MainActivity
import com.sdjic.gradnet.presentation.core.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import java.net.URL

// android implementation
actual fun getContactsUtil(): ContactsUtil {

    val context = MainActivity.instance

    return object : ContactsUtil {
        override fun sendSms(phoneNumber: String, message: String) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "smsto:$phoneNumber".toUri()
                putExtra("sms_body", message)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                println(e)
            }
        }

        override fun openLink(link: String) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = link.toUri()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                println(e)
            }
        }

        override fun dialPhoneNumber(phoneNumber: String) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:$phoneNumber".toUri()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }

        override fun sendEmail(email: String, subject: String, body: String) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:$email".toUri()
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }

        override fun shareText(text: String) {
            share(context =PlatformContext(context),text)
        }

        override fun sharePost(post: Post) {
            sharePost(context, post.images, post.content)
        }
    }

}

fun sharePost(context: Context, imageUrls: List<String>, description: String) {
    Toast.makeText(context, "sharing content, Please wait...", Toast.LENGTH_SHORT).show()
    CoroutineScope(Dispatchers.IO).launch {
        val imageUris = imageUrls.mapNotNull { url ->
            try {
                val bitmap = BitmapFactory.decodeStream(URL(url).openStream())
                val uri = saveImageToPublicStorage(context, bitmap) // ✅ Use the new function
                uri
            } catch (e: Exception) {
                Log.e("SaveImage", "Error saving image", e)
                null
            }
        }

        withContext(Dispatchers.Main) {
            if (imageUris.isEmpty() && description.isBlank()) {
                Toast.makeText(context, "Nothing to share", Toast.LENGTH_SHORT).show()
                return@withContext
            }

            val intent = if (imageUris.isNotEmpty()) {
                Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                    type = "image/*"
                    putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(imageUris))
                }
            } else {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                }
            }

            intent.putExtra(Intent.EXTRA_TEXT, "$description\n\nShared via GradNet")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            context.startActivity(Intent.createChooser(intent, "Share Post"))
        }
    }
}


private fun saveImageToPublicStorage(context: Context, bitmap: Bitmap): Uri? {
    val filename = "shared_image_${System.currentTimeMillis()}.jpg"

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES) // Save to Pictures folder
    }

    val resolver = context.contentResolver
    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    imageUri?.let { uri ->
        try {
            resolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
        } catch (e: IOException) {
            Log.e("SaveImage", "Error saving image", e)
            return null
        }
    }
    return imageUri
}
