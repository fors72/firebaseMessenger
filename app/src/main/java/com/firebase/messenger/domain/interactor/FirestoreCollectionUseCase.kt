package com.firebase.messenger.domain.interactor

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Observable
import io.reactivex.Observer
import javax.inject.Inject

open class FirestoreCollectionUseCase @Inject constructor(): UseCase<QuerySnapshot, Query>() {
    override fun buildUseCaseObservable(params: Query?): Observable<QuerySnapshot> {
        return FirestoreCollectionObservable(params!!)

    }

    inner class FirestoreCollectionObservable(private val query: Query):Observable<QuerySnapshot>(){
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
}