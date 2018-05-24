package com.firebase.messenger.presentation.view.holder

import android.view.View
import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.presentation.navigation.Navigator
import com.firebase.messenger.presentation.view.adapter.SRAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_holder.view.*
import org.jetbrains.anko.alert

class DialogHolder(val view: View, adapter: SRAdapter<Dialog>): SRAdapter.SRViewHolder<Dialog>(view,adapter) {

    override fun bindHolder(dialog: Dialog) {
        view.apply {
            name.text = dialog.name
            message.text = dialog.lastMessage?.message
            setOnClickListener {
//                FirebaseFirestore.getInstance().document(dialog.documentPath!!).set(dialog.apply { name += "1" }).addOnCompleteListener {  }
                Navigator.navigateToChat(adapter.context,dialog.documentPath!!)
            }
            setOnLongClickListener {
                adapter.context.alert {
                    title("qweew") }.show()
                true
            }
        }

    }
}