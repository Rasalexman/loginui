
package com.mincor.login.application

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.mincor.login.mediators.login.LoginMediator
import com.mincor.flairframework.ext.flair
import com.mincor.flairframework.interfaces.registerMediator

/**
 * Description：MainApplication
 * Created by：Alex
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // регистрируем стартовый медиатор
        flair {
            registerMediator<LoginMediator>()
        }
        Kotpref.init(this)
    }
}
