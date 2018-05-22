package com.firebase.messenger.presentation.view.holder

import android.view.View
import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.data.model.User
import com.firebase.messenger.presentation.view.adapter.SRAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_holder.view.*

class UserHolder(val view: View, adapter: SRAdapter<User>): SRAdapter.SRViewHolder<User>(view,adapter) {

    override fun bindHolder(user: User) {
        view.apply {
            name.text = user.name
            message.text = ""
            setOnClickListener {

            }
        }
    }
}