package com.firebase.messenger.presentation.view.holder

import android.view.View
import com.firebase.messenger.data.model.User
import com.firebase.messenger.dialogPathWithMe
import com.firebase.messenger.presentation.view.activity.ChatActivity
import com.firebase.messenger.presentation.view.adapter.SRAdapter
import kotlinx.android.synthetic.main.dialog_holder.view.*
import org.jetbrains.anko.startActivity

class UserHolder(val view: View, adapter: SRAdapter<User>): SRAdapter.SRViewHolder<User>(view,adapter) {

    override fun bindHolder(user: User) {
        view.apply {
            name.text = user.name
            message.text = ""
            setOnClickListener {
                adapter.context.startActivity<ChatActivity>("dialogId" to "/dialogs/${user.dialogPathWithMe()}")
            }
        }
    }
}