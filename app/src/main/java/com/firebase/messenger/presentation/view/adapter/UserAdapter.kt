package com.firebase.messenger.presentation.view.adapter

import android.content.Context
import android.util.Pair
import com.firebase.messenger.R
import com.firebase.messenger.data.model.User
import com.firebase.messenger.presentation.di.scope.PerActivity
import com.firebase.messenger.presentation.view.holder.UserHolder
import javax.inject.Inject

@PerActivity
class UserAdapter @Inject constructor(context: Context): QuerySnapshotAdapter<User>(context, mutableListOf()) {

    private var holderPair: Pair<Class<out SRViewHolder<User>>, Int> = Pair(UserHolder::class.java, R.layout.dialog_holder)
    override fun getHolderType(dialog: User?): Pair<Class<out SRViewHolder<User>>, Int> {
        return holderPair
    }
}