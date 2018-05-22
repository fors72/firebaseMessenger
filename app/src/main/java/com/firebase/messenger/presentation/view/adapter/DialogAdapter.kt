package com.firebase.messenger.presentation.view.adapter

import android.content.Context
import android.util.Pair
import com.firebase.messenger.R
import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.presentation.di.scope.PerActivity
import com.firebase.messenger.presentation.view.holder.DialogHolder
import javax.inject.Inject
@PerActivity
class DialogAdapter @Inject constructor(context:Context): QuerySnapshotAdapter<Dialog>(context, mutableListOf()) {

    private var holderPair:Pair<Class<out SRViewHolder<Dialog>>, Int> = Pair(DialogHolder::class.java, R.layout.dialog_holder)
    override fun getHolderType(dialog: Dialog?): Pair<Class<out SRViewHolder<Dialog>>, Int> {

        return holderPair
    }
}