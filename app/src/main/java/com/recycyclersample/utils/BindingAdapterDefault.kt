package com.recycyclersample.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import java.io.File

/**
 * Created by Dyzbee on 11/11/2017.
 */
object BindingAdapterDefault {
    @BindingAdapter(value = ["android:src", "placeholderImage", "errorImage"], requireAll = false)
    @JvmStatic
    fun loadImageWithGlide(imageView: ImageView, src: Any?, placeholder: Any?, errorImage: Any?) {
        if (src == null) {
            if (placeholder != null) {
                if (placeholder is Drawable) imageView.setImageDrawable(placeholder as Drawable?)
                if (placeholder is Int) imageView.setImageResource((placeholder as Int?)!!)
            }
            return
        }
        val options = RequestOptions()
        if (placeholder != null) {
            if (placeholder is Drawable) options.placeholder(placeholder as Drawable?)
            if (placeholder is Int) options.placeholder(placeholder)
        }
        if (errorImage != null) {
            if (errorImage is Drawable) options.error(errorImage as Drawable?)
            if (errorImage is Int) options.error(errorImage)
        }
        val manager: RequestManager =
            Glide.with(imageView.context).applyDefaultRequestOptions(options)
        val builder: RequestBuilder<Drawable> = when (src) {
            is String -> {
                manager.load(src as String?)
            }
            is Uri -> manager.load(src as Uri?)
            is Drawable -> manager.load(
                src as Drawable?
            )
            is Bitmap -> manager.load(src as Bitmap?)
            is Int -> manager.load(src as Int?)
            is File -> manager.load(
                src as File?
            )
            is ByteArray -> manager.load(src as ByteArray?)
            else -> manager.load(src)
        }
        builder.into(imageView)
    }

}