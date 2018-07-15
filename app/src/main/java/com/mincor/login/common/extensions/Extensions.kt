package com.mincor.login.common.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.view.ContextThemeWrapper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.mincor.askme.common.components.ExpandableTextView
import com.mincor.flairframework.BuildConfig
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.dip
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.forEachChild


inline fun log(lambda: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d("KOTLIN_TAG", lambda())
    }
}

inline fun ViewManager.expandableTextView(message: String = "", lineCount: Int = 3, init: ExpandableTextView.() -> Unit): ExpandableTextView {
    return ankoView({ ExpandableTextView(it) }, theme = 0) {
        init()
        if (message.isNotEmpty()) makeExpandable(message, lineCount)
    }
}

fun ExpandableTextView.text(message: String = "", lineCount: Int = 3) {
    this.text = message
    this.makeExpandable(message, lineCount)
}

@SuppressLint("RestrictedApi")
inline fun ViewManager.styledButton(textres: Int, styleRes: Int = 0, init: Button.() -> Unit): Button {
    return ankoView({ if (styleRes == 0) Button(it) else Button(ContextThemeWrapper(it, styleRes), null, 0) }, 0) {
        init()
        setText(textres)
    }
}

/***
 * Custom View For somethings like lines
 * */
fun roundedBg(col: Int, corners: Float = 100f, withStroke: Boolean = false, strokeColor: Int = Color.LTGRAY, strokeWeight: Int = 2) = GradientDrawable().apply {
    shape = GradientDrawable.RECTANGLE
    cornerRadius = corners
    setColor(col)
    if (withStroke) setStroke(strokeWeight, strokeColor)
}

fun gradientBg(colors: Array<Int>, orient: GradientDrawable.Orientation = GradientDrawable.Orientation.BOTTOM_TOP): GradientDrawable = GradientDrawable(orient, colors.toIntArray()).apply {
    shape = GradientDrawable.RECTANGLE
}

fun View.drawable(@DrawableRes resource: Int): Drawable? = ContextCompat.getDrawable(context, resource)
fun View.color(@ColorRes resource: Int): Int = ContextCompat.getColor(context, resource)
fun View.string(stringRes: Int): String = context.getString(stringRes)
fun Context.wdthProc(proc: Float): Int = (this.displayMetrics.widthPixels * proc).toInt()
fun View.wdthProc(proc: Float): Int = (context.displayMetrics.widthPixels * proc).toInt()
fun Context.hdthProc(proc: Float): Int = (this.displayMetrics.heightPixels * proc).toInt()
fun View.hdthProc(proc: Float): Int = (context.displayMetrics.heightPixels * proc).toInt()

fun String.isEmailValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

var margin16: Int = 0
var margin8 = 0
var margin4 = 0
fun View.dip16(): Int {
    if (margin16 == 0) {
        margin16 = dip(16)
    }
    return margin16
}

fun View.dip8(): Int {
    if (margin8 == 0) {
        margin8 = dip(8)
    }
    return margin8
}

fun View.dip4(): Int {
    if (margin4 == 0) {
        margin4 = dip(4)
    }
    return margin4
}

fun ImageView.clear() {
    this.setImageResource(0)
    this.setImageBitmap(null)
    this.setImageDrawable(null)
}

fun ViewGroup.clear() {
    var childView: View
    repeat(this.childCount) {
        childView = this.getChildAt(it)
        when (childView) {
            is ViewGroup -> (childView as ViewGroup).clear()
            is ImageView -> (childView as ImageView).clear()
            is Button -> (childView as Button).setOnClickListener(null)
            is TextView -> (childView as TextView).text = null
        }
    }
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

/**
 * Sets the view's visibility to INVISIBLE
 */
/*fun View.invisible() {
    visibility = View.INVISIBLE
}*/
/**
 * Toggle's view's visibility. If View is visible, then sets to gone. Else sets Visible
 */
fun View.toggle() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

var View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun ViewGroup.enableDisable(isEnable: Boolean = false) {
    this.forEachChild {
        it.isEnabled = isEnable
        if (it is ViewGroup) {
            it.enableDisable(isEnable)
        }
    }
}


/**
 * Created by a.minkin on 23.01.2018.
 */
interface IPhotoLoaderListener {
    fun onPhotoLoadedHandler()
    fun onPhotoFailedHandler()
}