package com.firebase.messenger.presentation.view.impl.base

import android.content.Context

interface LoadView {


    fun showLoading(message: String)

    fun hideLoading()

    fun showError(message: String)

}