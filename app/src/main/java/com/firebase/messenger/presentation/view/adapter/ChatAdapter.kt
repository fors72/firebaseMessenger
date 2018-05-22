package com.firebase.messenger.presentation.view.adapter

import android.content.Context
import android.util.Pair
import com.firebase.messenger.R
import com.firebase.messenger.data.model.Message
import com.firebase.messenger.presentation.di.scope.PerActivity
import com.firebase.messenger.presentation.view.holder.MessageHolder
import javax.inject.Inject


@PerActivity
class ChatAdapter @Inject constructor(context: Context): QuerySnapshotAdapter<Message>(context, mutableListOf()) {

    private var holderPair: Pair<Class<out SRViewHolder<Message>>, Int> = Pair(MessageHolder::class.java, R.layout.dialog_holder)
    override fun getHolderType(dialog: Message?): Pair<Class<out SRViewHolder<Message>>, Int> {
        return holderPair
    }
}