package com.firebase.messenger.presentation.view.impl

import com.firebase.messenger.presentation.view.impl.base.LoadView

interface AuthView:LoadView {

    fun onSuccessAuth()

    fun createProfile()


}