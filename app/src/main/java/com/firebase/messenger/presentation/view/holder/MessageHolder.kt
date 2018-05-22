package com.firebase.messenger.presentation.view.holder

import android.view.View
import com.firebase.messenger.data.model.Message
import com.firebase.messenger.presentation.view.adapter.SRAdapter
import kotlinx.android.synthetic.main.dialog_holder.view.*


class MessageHolder(val view: View, adapter: SRAdapter<Message>): SRAdapter.SRViewHolder<Message>(view,adapter) {

    override fun bindHolder(message1: Message) {
        view.apply {
            name.text = message1.message
            message.text = message1.time.toString()
//            message.text = dialog.documentPath
//            setOnClickListener {
//                FirebaseFirestore.getInstance().document(dialog.documentPath!!).set(dialog.apply { name += "1" }).addOnCompleteListener {  }
//            }
//            setOnLongClickListener {
//                adapter.context.alert {
//                    title("qweew") }.show()
//                true
//            }
        }

    }
}