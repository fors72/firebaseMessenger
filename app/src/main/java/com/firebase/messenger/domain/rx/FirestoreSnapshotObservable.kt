package com.firebase.messenger.domain.rx

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Observable
import io.reactivex.Observer

class FirestoreSnapshotObservable(private val query: Query): Observable<QuerySnapshot>(){
    override fun subscribeActual(observer: Observer<in QuerySnapshot>) {
        query.addSnapshotListener{querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException == null){
                querySnapshot?.let { observer.onNext(querySnapshot) }
            }else{
                observer.onError(firebaseFirestoreException)
            }
        }
    }
}