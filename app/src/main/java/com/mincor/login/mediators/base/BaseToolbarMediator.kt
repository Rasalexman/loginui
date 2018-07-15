package com.mincor.login.mediators.base

import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import com.mincor.flairframework.core.animation.LinearAnimator
import com.mincor.flairframework.interfaces.activity
import com.mincor.flairframework.interfaces.common.IActionBarProvider
import com.mincor.flairframework.interfaces.popToBack
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent


/**
 * Created by a.minkin.
 */
abstract class BaseToolbarMediator : BaseMediator(), View.OnClickListener {

    companion object {
        const val BACK_BUTTON_ID = -1
    }

    protected var toolBar: Toolbar? = null

    override fun onCreatedView(view: View) {
        super.onCreatedView(view)
        toolBar?.let {
            setActionBar(it)
        } ?: createToolbar(view as? ViewGroup)
    }

    private fun createToolbar(view:ViewGroup?) {
        view?.let {
            val layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, activity.dip(56))
            val appbar = AppBarLayout(activity)
            appbar.layoutParams = layoutParams
            toolBar = appbar.toolbar {
                lparams(matchParent, dip(56))
                setTitleTextColor(Color.WHITE)
                title = "HELLO WORLD"
            }

            it.addView(appbar, 0)
        }
        setActionBar(toolBar)
    }

    override fun onRemovedView() {
        super.onRemovedView()
        toolBar?.setNavigationOnClickListener(null)
        toolBar = null
    }

    private val actionBar: ActionBar?
        get() {
            val actionBarProvider = (activity as? IActionBarProvider<ActionBar, Toolbar>)
            return actionBarProvider?.getSupportActionBar()
        }

    private fun setActionBar(toolbar: Toolbar?) {
        toolbar?.let {
            (activity as? IActionBarProvider<ActionBar, Toolbar>)?.setSupportActionBar(it)
            it.setNavigationOnClickListener(this)
            it.title = title
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            BACK_BUTTON_ID -> popToBack(LinearAnimator())
        }
    }

    protected fun setHomeButtonEnable() {
        //set the back arrow in the toolbar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

