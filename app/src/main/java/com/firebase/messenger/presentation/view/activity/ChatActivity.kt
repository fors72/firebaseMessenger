package com.firebase.messenger.presentation.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.firebase.messenger.R
import com.firebase.messenger.data.model.Message
import com.firebase.messenger.presentation.di.Injector
import com.firebase.messenger.presentation.presenter.ChatPresenter
import com.firebase.messenger.presentation.view.adapter.ChatAdapter
import com.firebase.messenger.presentation.view.impl.ChatView
import com.firebase.messenger.toModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_chat.*
import javax.inject.Inject

class ChatActivity : AppCompatActivity(), ChatView {
    override fun renderQuery(querySnapshot: QuerySnapshot) {
        adapter.renderQuery(querySnapshot)
    }

    override fun initQuery(documents: List<DocumentSnapshot>) {
        if (adapter.itemCount > 0)return
        adapter.list = documents.toModel(Message::class)
        adapter.notifyDataSetChanged()
    }

    @Inject
    lateinit var presenter: ChatPresenter
    lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        Injector.getChatComponent().inject(this)
        adapter = ChatAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,true)

        presenter.attachView(this)
        presenter.initialize(intent.getStringExtra("dialogId"))


        sentBtn.setOnClickListener{
            if (messageField.text.toString().isEmpty())return@setOnClickListener
            FirebaseFirestore.getInstance().collection("${intent.getStringExtra("dialogId")}/messages").add(Message(messageField.text.toString())).addOnCompleteListener {  }
            messageField.text.clear()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(isFinishing)
    }
}
