package com.mincor.login.mediators.base

import android.content.Context
import android.view.View
import com.mincor.flairframework.patterns.mediator.Mediator

abstract class ViewBindController : Mediator() {

    abstract override fun createLayout(context: Context): View
}
