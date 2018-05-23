package com.firebase.messenger.presentation.view.impl

import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.presentation.view.impl.base.RenderQueryListener

interface ChatView:RenderQueryListener {

    fun renderDialog(dialog: Dialog)
}