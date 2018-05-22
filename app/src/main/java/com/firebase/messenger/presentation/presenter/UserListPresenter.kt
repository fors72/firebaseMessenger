package com.firebase.messenger.presentation.presenter

import android.util.Log
import com.firebase.messenger.domain.interactor.FirestoreCollectionUseCase
import com.firebase.messenger.domain.rx.DefaultObserver
import com.firebase.messenger.presentation.di.Injector
import com.firebase.messenger.presentation.di.scope.PerActivity
import com.firebase.messenger.presentation.view.impl.base.RenderQueryListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

@PerActivity
class UserListPresenter @Inject constructor(private val useCase: FirestoreCollectionUseCase):BasePresenter<RenderQueryListener,List<DocumentSnapshot>>() {

    var isLoading = false

    private val query = FirebaseFirestore.getInstance()
            .collection("users")

    fun initialize(limit:Int = 40){
        if (isLoading || model != null)return
        isLoading = true
        useCase.clean()
        useCase.execute(query.limit(limit.toLong()),DialogObserver())
    }


    override fun updateView() {
        model?.let { view()?.initQuery(it) }
    }


    public override fun destroy() {
        useCase.dispose()
        Injector.clearActivityComponent()
    }

    inner class DialogObserver: DefaultObserver<QuerySnapshot>() {

        override fun onNext(t: QuerySnapshot) {
            model = t.documents
            if (!isLoading){
                view()?.renderQuery(t)
            }else{
                view()?.initQuery(t.documents)
                isLoading = false
            }
        }

        override fun onError(e: Throwable) {
            Log.e("err",e.message)
        }


    }
}