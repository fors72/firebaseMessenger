package com.firebase.messenger.presentation.presenter

import java.lang.ref.WeakReference



abstract class BasePresenter<V,M> {

    protected var model: M? = null

    protected var view: WeakReference<V?>? = null


    fun updateModel(model: M) {
        this.model = model
        if (setupDone()) {
            updateView()
        }
    }


    fun attachView(view: V){
        this.view = WeakReference(view)
        if (setupDone()) {
            updateView()
        }

    }

    fun detachView(isFinishing:Boolean){
        view = null
        if (isFinishing) destroy()
    }

    protected fun view(): V? {
        return if (view == null) {
            null
        } else {
            view!!.get()
        }
    }

    protected abstract fun updateView()
    protected abstract fun destroy()



    private fun setupDone(): Boolean {
        return view() != null && model != null
    }

}