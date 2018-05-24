package com.firebase.messenger.domain.rx

import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.data.model.DialogUpdate
import com.firebase.messenger.data.model.Message
import com.firebase.messenger.toUpdateModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import io.reactivex.Observable
import io.reactivex.Observer
import java.util.*

class SentMessageObservable(private val path:String, val message: Message): Observable<Unit>() {
    override fun subscribeActual(observer: Observer<in Unit>) {
        val db = FirebaseFirestore.getInstance()
        db.collection("$path/messages").document().set(message).addOnSuccessListener {
            db.runTransaction{ transaction ->
                val dialogSnap = transaction.get(db.document(path))
                if (dialogSnap.exists()){
                    val dialog = dialogSnap.toObject(Dialog::class.java)!!
                    dialog.lastMessage = message
                    transaction.set(db.document(path), dialog.toUpdateModel())
                }
            }.addOnCompleteListener {
                if (it.exception == null){
                    observer.onNext(Unit)
                    observer.onComplete()
                }else{
                    observer.onError(it.exception!!)
                }
            }
        }.addOnFailureListener {
            db.runTransaction{ transaction ->
                val dialogId = path.replace("/dialogs/","")
                val user = FirebaseAuth.getInstance().currentUser!!.uid
                if (!dialogId.contains(user)) throw FirebaseFirestoreException("only user to user dialog creation supported",FirebaseFirestoreException.Code.ABORTED)
                val user2 = dialogId.replace(user,"")
                transaction.set(db.document(path),Dialog(lastMessage = message, users = hashMapOf(user to Date(),user2 to Date())).toUpdateModel())
                return@runTransaction
            }.addOnSuccessListener {
                db.collection("$path/messages").document().set(message).addOnSuccessListener {
                    observer.onNext(Unit)
                    observer.onComplete()
                }.addOnFailureListener {
                    observer.onError(it)
                }
            }.addOnFailureListener {
                observer.onError(it)
            }
        }





    }
}