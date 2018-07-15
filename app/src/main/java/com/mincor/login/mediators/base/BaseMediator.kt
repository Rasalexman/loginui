package com.mincor.login.mediators.base

import android.view.View
import android.view.ViewGroup
import com.mincor.login.common.extensions.enableDisable

abstract class BaseMediator : ViewBindController() {

    override fun onCreatedView(view: View) {
        super.onCreatedView(view)
        attachListeners()
    }

    override fun onDestroyView() {
        detachListeners()
        super.onDestroyView()
    }

    /**
     * Блокируем нажатие на элементы при переключении ( анимации )
     */
    fun disableOrEnableView(isEnable:Boolean = true){
        (viewComponent as? ViewGroup)?.enableDisable(isEnable)
    }

    /**
     * Назначаем слушателей для текущего Контроллера
     */
    protected open fun attachListeners() {}

    /**
     * Удаляем слушателей для текущего контроллера
     */
    protected open fun detachListeners() {}

    protected open var title: String = ""
}
