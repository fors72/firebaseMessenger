package com.firebase.messenger.domain.rx

import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.data.model.Message
import com.firebase.messenger.toUpdateModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import io.reactivex.Observable
import io.reactivex.Observer
import java.util.*

class CreateUserDialog(private val dialogPath:String, val message: Message): Observable<Unit>()  {
    override fun subscribeActual(observer: Observer<in Unit>) {
        val db = FirebaseFirestore.getInstance()
        db.runTransaction{ transaction ->
            val dialogId = dialogPath.replace("/dialogs/","")
            val user = FirebaseAuth.getInstance().currentUser!!.uid
            if (!dialogId.contains(user)) throw FirebaseFirestoreException("only user to user dialog creation supported", FirebaseFirestoreException.Code.ABORTED)
            val user2 = dialogId.replaceFirst(user,"")
            transaction.set(db.document(dialogPath), Dialog(lastMessage = message, users = hashMapOf(user to Date(),user2 to Date())).toUpdateModel())
            return@runTransaction
        }.addOnSuccessListener {
            observer.onNext(Unit)
            observer.onComplete()
        }.addOnFailureListener {
            observer.onError(it)
        }
    }
}