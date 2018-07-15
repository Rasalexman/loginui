package com.mincor.login.mediators.login

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import com.mincor.flairframework.core.animation.LinearAnimator
import com.mincor.flairframework.interfaces.mediatorLazy
import com.mincor.flairframework.interfaces.show
import com.mincor.flairframework.interfaces.showMediator
import com.mincor.flairframework.interfaces.stringRes
import com.mincor.flairframework.patterns.mediator.Mediator
import com.mincor.login.R
import com.mincor.login.common.components.Prefs
import com.mincor.login.common.extensions.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by Alex on 22.01.2017.
 */

class LoginMediator : Mediator() {

    override fun createLayout(context: Context): View = LoginUI().createView(AnkoContext.create(context, this))
    private val registerMediator: RegistrationMediator by mediatorLazy()
    private val forgotPassMediator: ForgotPassMediator by mediatorLazy()

    private var emailET:EditText? = null
    private var passwordET:EditText? = null

    internal fun singInUser() {

        // Reset errors.
        emailET!!.error = null
        passwordET!!.error = null

        val emailStr = emailET!!.text.toString()
        val passStr = passwordET!!.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid emailET address.
        // There was an error; don't attempt login and focus the first
        // form field with an error.
        when {
            emailStr.isEmpty() || !emailStr.isEmailValid() -> {
                emailET!!.error = stringRes(R.string.notSetEmailTF)
                focusView = emailET
                cancel = true
            }
            passStr.isEmpty() -> {       // Check for a valid password, if the user entered one.
                passwordET!!.error = stringRes(R.string.notSetPassTF)
                focusView = passwordET
                cancel = true
            }
            passStr.length < 5 -> {  // Check for a valid password, if the user entered one.
                passwordET!!.error = stringRes(R.string.minPassErrorTF) + " " + 5
                focusView = passwordET
                cancel = true
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            Prefs.login = emailStr
            Prefs.pass = passStr
            showMediator<MainMediator>(null, LinearAnimator())
        }
    }


    private inner class LoginUI : AnkoComponent<LoginMediator> {
        override fun createView(ui: AnkoContext<LoginMediator>): View = with(ui) {
            verticalLayout {
                background = gradientBg(arrayOf(color(R.color.startColor), color(R.color.endColor)))
                gravity = Gravity.CENTER

                emailET = editText(Prefs.login) {
                    background = roundedBg(Color.WHITE, 16f, true)
                    textSize = 14f
                    textColor = color(R.color.colorPrimaryText)
                    hint = string(R.string.emailTF)
                    setPadding(dip8(), dip8(), dip8(), dip8())
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }.lparams(wdthProc(0.6f))

                passwordET = editText {
                    background = roundedBg(Color.WHITE, 16f, true)
                    textSize = 14f
                    textColor = color(R.color.colorPrimaryText)
                    hint = string(R.string.passTF)
                    setPadding(dip8(), dip8(), dip8(), dip8())
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }.lparams(wdthProc(0.6f)) {
                    topMargin = dip8()
                }

                button(R.string.logInTF) {
                    background = roundedBg(Color.WHITE, 16f, true)
                    textSize = 14f
                    onClick {
                        singInUser()
                    }
                }.lparams(wdthProc(0.6f), hdthProc(0.06f)) {
                    topMargin = dip8()
                }

                button(R.string.registerTF) {
                    backgroundColor= Color.TRANSPARENT
                    textSize = 12f
                    textColor = Color.WHITE
                    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG

                    onClick {
                        registerMediator.show(LinearAnimator())
                    }
                }.lparams(wdthProc(0.6f)) {
                    topMargin = dip8()
                }

                button(R.string.forgotPassTF) {
                    backgroundColor= Color.TRANSPARENT
                    textSize = 12f
                    textColor = Color.WHITE
                    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG

                    onClick {
                        forgotPassMediator.show(LinearAnimator())
                    }
                }.lparams(wdthProc(0.6f))
            }
        }
    }
}
