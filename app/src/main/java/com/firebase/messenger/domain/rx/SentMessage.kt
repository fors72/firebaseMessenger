package com.firebase.messenger.domain.rx

import com.firebase.messenger.data.model.Message
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.Observer

class SentMessage(private val dialogPath:String, val message: Message): Observable<Unit>() {
    override fun subscribeActual(observer: Observer<in Unit>) {
        FirebaseFirestore.getInstance().collection("$dialogPath/messages").document().set(message).addOnCompleteListener {
            if (it.exception == null){
                observer.onNext(Unit)
                observer.onComplete()
            }else{
                observer.onError(it.exception!!)
            }
        }
    }
}