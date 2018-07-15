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
import com.mincor.login.common.components.Prefs
import com.mincor.login.common.extensions.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by Alex on 22.01.2017.
 */

class RegistrationMediator : Mediator() {
    override fun createLayout(context: Context): View = RegisterUI().createView(AnkoContext.create(context, this))

    private var nameET: EditText? = null
    private var emailET: EditText? = null
    private var passwordET: EditText? = null
    private var repeatePasswordET: EditText? = null

    internal fun registerUser() {
        // Reset errors.
        nameET!!.error = null
        emailET!!.error = null
        passwordET!!.error = null
        repeatePasswordET!!.error = null

        val nameStr = nameET!!.text.toString()
        val emailStr = emailET!!.text.toString()
        val passStr = passwordET!!.text.toString()
        val repeatePassStr = repeatePasswordET!!.text.toString()

        var cancel = false
        var focusView: View? = null

        if (nameStr.isEmpty() || nameStr.length == 1) {
            nameET!!.error = stringRes(R.string.notSetNameTF)
            focusView = nameET
            cancel = true
        } else if (emailStr.isEmpty() || !emailStr.isEmailValid()) {
            emailET!!.error = stringRes(R.string.notSetEmailTF)
            focusView = emailET
            cancel = true
        } else if (passStr.isEmpty()) {       // Check for a valid password, if the user entered one.
            passwordET!!.error = stringRes(R.string.notSetPassTF)
            focusView = passwordET
            cancel = true
        } else if (passStr.length < 5) {       // Check for a valid password, if the user entered one.
            passwordET!!.error = "${stringRes(R.string.minPassErrorTF)} 5"
            focusView = passwordET
            cancel = true
        } else if (passStr != repeatePassStr) {  // Check for a valid password, if the user entered one.
            repeatePasswordET!!.error = stringRes(R.string.notEqualPassTF)
            focusView = repeatePasswordET
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            Prefs.name = nameStr
            Prefs.login = emailStr
            Prefs.pass = passStr
            popToBack(LinearAnimator())
        }
    }

    private inner class RegisterUI : AnkoComponent<RegistrationMediator> {
        override fun createView(ui: AnkoContext<RegistrationMediator>): View = with(ui) {
            verticalLayout {
                background = gradientBg(arrayOf(color(R.color.startColor), color(R.color.endColor)))
                gravity = Gravity.CENTER

                nameET = editText {
                    background = roundedBg(Color.WHITE, 16f, true)
                    textSize = 14f
                    textColor = color(R.color.colorPrimaryText)
                    hint = string(R.string.nameTF)
                    setPadding(dip8(), dip8(), dip8(), dip8())
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                }.lparams(wdthProc(0.6f))

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

                repeatePasswordET = editText {
                    background = roundedBg(Color.WHITE, 16f, true)
                    textSize = 14f
                    textColor = color(R.color.colorPrimaryText)
                    hint = string(R.string.passRepeatTF)
                    setPadding(dip8(), dip8(), dip8(), dip8())
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }.lparams(wdthProc(0.6f)) {
                    topMargin = dip8()
                }

                button(R.string.submitTF) {
                    background = roundedBg(Color.WHITE, 16f, true)
                    textSize = 14f
                    onClick {
                        registerUser()
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
