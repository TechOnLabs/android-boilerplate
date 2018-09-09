package com.techonlabs.androidboilerplate.utils

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.techonlabs.androidboilerplate.utils.extensions.loadImg


object BindingAdapters {
    @BindingAdapter("app:loadImgUrl")
    @JvmStatic
    fun loadImgUrl(view: AppCompatImageView, url: String) {
        view.loadImg(url)
    }

    @BindingAdapter("app:imageRes")
    @JvmStatic
    fun setImgRes(view: AppCompatImageView, @DrawableRes resId: Int) {
        view.setImageResource(resId)
    }

    @BindingAdapter("app:textRes")
    @JvmStatic
    fun setTextRes(view: AppCompatTextView, resId: Int) {
        view.setText(resId)
    }

    @BindingAdapter("app:hideIfZero")
    @JvmStatic
    fun hideIfZero(view: View, number: Int) {
        view.visibility = if (number == 0) View.GONE else View.VISIBLE
    }

    @BindingAdapter("app:invisiblilty")
    @JvmStatic
    fun invisiblilty(view: View, bool: Boolean) {
        view.visibility = if (bool) View.INVISIBLE else View.VISIBLE
    }

    @BindingAdapter("app:clipToOutline")
    @JvmStatic
    fun clipToBackground(view: View, boolean: Boolean) {
        view.clipToOutline = boolean
    }

}