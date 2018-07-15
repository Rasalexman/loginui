package com.mincor.askme.common.components

import android.content.Context
import android.widget.TextView
import android.text.style.ClickableSpan
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewTreeObserver
import android.os.Build
import android.annotation.TargetApi
import android.util.AttributeSet


/**
 * Created by a.minkin on 01.11.2017.
 */
class ExpandableTextView : TextView {

    private val TAG = "ExpandableTextView"
    private val ELLIPSIZE = "... "
    private val MORE = "еще"
    private val LESS = "свернуть"

    private var mFullText: String? = null
    private var mMaxLines: Int = 0

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int):super(context, attrs, defStyleAttr)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

    fun makeExpandable(maxLines: Int) {
        makeExpandable(text.toString(), maxLines)
    }

    fun makeExpandable(fullText: String, maxLines: Int) {
        mFullText = fullText
        mMaxLines = maxLines
        val vto = viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val obs = viewTreeObserver
                obs.removeOnGlobalLayoutListener(this)
                if (lineCount <= maxLines) {
                    text = mFullText
                } else {
                    movementMethod = LinkMovementMethod.getInstance()
                    showLess()
                }
            }
        })
    }

    /**
     * truncate text and append a clickable [.MORE]
     */
    private fun showLess() {
        val lineEndIndex = layout.getLineEnd(mMaxLines - 1)
        val newText = (mFullText!!.substring(0, lineEndIndex - (ELLIPSIZE.length + MORE.length + 1))
                + ELLIPSIZE + MORE)
        val builder = SpannableStringBuilder(newText)
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                showMore()
            }
        }, newText.length - MORE.length, newText.length, 0)
        setText(builder, TextView.BufferType.SPANNABLE)
    }

    /**
     * show full text and append a clickable [.LESS]
     */
    private fun showMore() {
        // create a text like subText + ELLIPSIZE + MORE
        val builder = SpannableStringBuilder(mFullText + LESS)
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                showLess()
            }
        }, builder.length - LESS.length, builder.length, 0)
        setText(builder, TextView.BufferType.SPANNABLE)
    }

}