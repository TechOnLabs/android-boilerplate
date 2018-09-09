@file:Suppress("NOTHING_TO_INLINE")

package com.techonlabs.androidboilerplate.utils.extensions

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.techonlabs.androidboilerplate.GlideApp
import com.techonlabs.androidboilerplate.R

fun <T : ViewDataBinding?> ViewGroup.bindView(layoutId: Int, attachToRoot: Boolean = false) =
        DataBindingUtil.inflate<T>(LayoutInflater.from(context), layoutId, this, attachToRoot)!!

fun View.setForegroundResourceBorderless(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true)
        this.foreground = ContextCompat.getDrawable(context, outValue.resourceId)
    }
}

fun View.setBackgroundResourceBorderless(context: Context) {
    val outValue = TypedValue()
    context.theme.resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true)
    this.background = ContextCompat.getDrawable(context, outValue.resourceId)

}

fun Context.attr(@AttrRes attribute: Int): TypedValue {
    val typed = TypedValue()
    theme.resolveAttribute(attribute, typed, true)
    return typed
}

fun View.setMargins(l: Int, t: Int, r: Int, b: Int) {

    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(l, t, r, b)
        this.requestLayout()
    }
}

fun View.setGradientBackground(@ColorRes color1Id: Int = R.color.colorPrimaryDark,
                               @ColorRes color2Id: Int = R.color.colorPrimary,
                               orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TL_BR) {
    val colors = intArrayOf(ContextCompat.getColor(context, color1Id), ContextCompat.getColor(context, color2Id))
    val gd = GradientDrawable(orientation, colors)
    this.background = gd
}

fun View.visibilityGone() {
    this.visibility = View.GONE
}

fun View.visibilityInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.visibilityVisible() {
    this.visibility = View.VISIBLE
}

inline val View.isVisible: Boolean
    get() = this.visibility == View.VISIBLE

inline val View.isInVisible: Boolean
    get() = this.visibility == View.INVISIBLE

inline val View.isGone: Boolean
    get() = this.visibility == View.GONE


inline val Context.displayMetrics: android.util.DisplayMetrics
    get() = resources.displayMetrics

fun Context.getColorFromAttr(@AttrRes attribute: Int): Int {
    val typedValue = TypedValue()
    val theme = this.theme
    theme.resolveAttribute(attribute, typedValue, true)
    return typedValue.data
}

fun Fragment.finish() {
    activity!!.finish()
}

inline val TextInputEditText.textStr
    get() = this.text.toString().trim()


fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun TextInputEditText.validate(validator: (String) -> Int?): Boolean {
    val messageId = validator(this.textStr)
    if (messageId != null) {
        error = context.resources.getString(messageId)
        return false
    }
    return true
}

fun TextInputEditText.validate(validator: (String) -> Boolean, @StringRes messageId: Int) =
        this.validate { if (validator(this.textStr)) null else messageId }

fun Fragment.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.adjustPan() {
    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
}

fun Fragment.adjustResize() {
    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

fun AppCompatImageView.loadImg(url: String) {
    val ro = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

    GlideApp.with(context)
            .applyDefaultRequestOptions(ro)
            .load(url)
            .centerCrop()
            .into(this)
}

