package com.example.allegrointernship.util

import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    //binding adapter that sets image from the Internet to the ImageView
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImageFromUrl(view: ImageView,imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }

    //binding adapter that defines visibility logic basing on the condition
    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibility(view:View,condition:Boolean)
    {
        view.visibility = if (condition) View.VISIBLE else View.GONE
    }
}