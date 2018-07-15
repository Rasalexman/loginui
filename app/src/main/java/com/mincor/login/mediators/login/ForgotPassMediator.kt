package com.mincor.login.mediators.login

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import com.mincor.flairframework.core.animation.LinearAnimator
import com.mincor.flairframework.interfaces.activity
import com.mincor.flairframework.interfaces.popToBack
import com.mincor.flairframework.interfaces.stringRes
import com.mincor.flairframework.patterns.mediator.Mediator
import com.mincor.login.R
import com.mincor.login.common.extensions.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by Alex on 22.01.2017.
 */

class ForgotPassMediator : Mediator() {
    override fun createLayout(context: Context): View = ForgotUI().createView(AnkoContext.Companion.create(context, this))

    internal var emailET: EditText? = null

    internal fun onSubmitHandler() {
        val emailStr = emailET!!.text.toString()
        if (emailStr.isEmpty() || !emailStr.isEmailValid()) {
            emailET!!.error = stringRes(R.string.notSetEmailTF)
            emailET!!.requestFocus()
        } else {
            activity.toast(R.string.accResponceTF)
            popToBack(LinearAnimator())
        }
    }

    private inner class ForgotUI : AnkoComponent<ForgotPassMediator> {
        override fun createView(ui: AnkoContext<ForgotPassMediator>): View = with(ui) {
            verticalLayout {
                background = gradientBg(arrayOf(color(R.color.startColor), color(R.color.endColor)))
                gravity = Gravity.CENTER

                emailET = editText {
                    background = roundedBg(Color.WHITE, 16f, true)
                    textSize = 14f
                    textColor = color(R.color.colorPrimaryText)
                    hint = string(R.string.emailTF)
                    setPadding(dip8(), dip8(), dip8(), dip8())
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }.lparams(wdthProc(0.6f)) {
                    topMargin = dip8()
                }

                button(R.string.submitTF) {
                    background = roundedBg(Color.WHITE, 16f, true)
                    textSize = 14f
                    onClick {
                        onSubmitHandler()
                    }
                }.lparams(wdthProc(0.6f), hdthProc(0.06f)) {
                    topMargin = dip8()
                }

                button(R.string.backTF) {
                    background = roundedBg(Color.WHITE, 16f, true)
                    textSize = 12f
                    textColor = color(R.color.colorPrimaryText)

                    onClick {
                        popToBack(LinearAnimator())
                    }
                }.lparams(wdthProc(0.4f), hdthProc(0.06f)) {
                    topMargin = dip8()
                }
            }
        }
    }
}
