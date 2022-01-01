package be.appwise.networking

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.webkit.MimeTypeMap
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.File

object NetworkingUtil {
    internal fun responseCount(responseMethod: Response?): Int {
        var response = responseMethod
        var result = 0
        while (response != null) {
            response = response.priorResponse
            if (response == response?.priorResponse) {
                result++
            }
        }
        return result
    }

    suspend fun getBodyForFile(
        contentResolver: ContentResolver,
        key: String,
        file: File
    ): MultipartBody.Part = withContext(Dispatchers.Default) {
        val requestFile =
            file.asRequestBody(getMimeType(contentResolver, Uri.fromFile(file)).toMediaTypeOrNull())
        MultipartBody.Part.createFormData(key, file.name, requestFile)
    }

    suspend fun getBodyForImage(context: Context, key: String, file: File): MultipartBody.Part =
        withContext(
            Dispatchers.Default
        ) {
            val requestFile = resizeFile(context, file)
                .asRequestBody(getMimeType(context.contentResolver, Uri.fromFile(file)).toMediaTypeOrNull())
            MultipartBody.Part.createFormData(key, file.name, requestFile)
        }

    suspend fun resizeFile(context: Context, file: File): File {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.path, options)

        val imageHeight = options.outHeight.toDouble()
        val imageWidth = options.outWidth.toDouble()
        //720p is 1280 x 720 px
        val newHeight: Double
        val newWidth: Double
        if (imageHeight > imageWidth) {//portrait-->720p
            val ratio = imageHeight / 720
            newWidth = imageWidth / ratio
            newHeight = 720.0
        } else {
            val ratio = imageWidth / 720
            newWidth = 720.0
            newHeight = imageHeight / ratio
        }

        return Compressor.compress(context, file) {
            default(newWidth.toInt(), newHeight.toInt(), quality = 80)
        }
    }

    /**
     * Get the mime type of a file by the uri.
     * To get the uri from a file just use 'Uri.fromFile(file)'
     *
     * @param cr The ContentResolver that will be used to check the Uri's type (get the contentResolver by doing 'context.contentResolver')
     * @param uri The uri to be checked for the mime type
     * @return The mime type of the file will be returned, if the mime type does not exist or is not correct "* / *" will be returned'
     */
    @SuppressLint("DefaultLocale")
    fun getMimeType(cr: ContentResolver, uri: Uri): String {
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            cr.getType(uri) ?: "*/*"
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase())
                ?: "*/*"
        }
    }
}