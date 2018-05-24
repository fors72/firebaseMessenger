package com.firebase.messenger.domain.rx

import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.data.model.Message
import com.firebase.messenger.toUpdateModel
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.Observer

class UpdateDialog(private val dialogPath:String, val message: Message): Observable<Unit>() {
    override fun subscribeActual(observer: Observer<in Unit>) {
        val db = FirebaseFirestore.getInstance()
        db.runTransaction{ transaction ->
            val dialogSnap = transaction.get(db.document(dialogPath))
            if (dialogSnap.exists()){
                val dialog = dialogSnap.toObject(Dialog::class.java)!!
                dialog.lastMessage = message
                transaction.set(db.document(dialogPath), dialog.toUpdateModel())
            }
        }.addOnCompleteListener {
            if (it.exception == null){
                observer.onNext(Unit)
                observer.onComplete()
            }else{
                observer.onError(it.exception!!)
            }
        }
    }
}