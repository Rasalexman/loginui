package com.mincor.login.common.components

import com.chibatching.kotpref.KotprefModel

object Prefs : KotprefModel() {
    var name:String by stringPref("")
    var login:String by stringPref("")
    var pass:String by stringPref("")
}