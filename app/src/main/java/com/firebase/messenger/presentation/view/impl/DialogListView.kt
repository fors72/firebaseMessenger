package com.firebase.messenger.presentation.view.impl

import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.presentation.view.impl.base.LoadView

interface DialogListView: LoadView {

    fun renderMessage(messages:Collection<Dialog>)
}