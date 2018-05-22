package com.firebase.messenger.presentation.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.messenger.R
import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.presentation.di.Injector
import com.firebase.messenger.presentation.presenter.DialogPresenter
import com.firebase.messenger.presentation.view.adapter.DialogAdapter
import com.firebase.messenger.presentation.view.impl.DialogView
import com.firebase.messenger.toModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.recycler_view.*
import javax.inject.Inject

class DialogFragment:BaseFragment(),DialogView {

    @Inject lateinit var dialogPresenter: DialogPresenter
    lateinit var adapter: DialogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.getActivityComponent().inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = DialogAdapter(activity!!)
        return inflater.inflate(R.layout.recycler_view,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        dialogPresenter.attachView(this)
        dialogPresenter.initialize()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogPresenter.detachView(activity?.isFinishing ?: true)
    }




    override fun renderQuery(querySnapshot: QuerySnapshot) {
        adapter.renderQuery(querySnapshot)
    }

    override fun initQuery(documents: List<DocumentSnapshot>) {
        if (adapter.itemCount > 0)return
        adapter.list = documents.toModel(Dialog::class)
        adapter.notifyDataSetChanged()
    }
}