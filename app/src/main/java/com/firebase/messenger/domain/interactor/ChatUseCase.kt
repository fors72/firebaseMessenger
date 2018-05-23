package com.firebase.messenger.domain.interactor

import com.firebase.messenger.data.model.Message
import com.firebase.messenger.domain.rx.DefaultObserver
import com.firebase.messenger.domain.rx.FirestoreSnapshotObservable
import com.firebase.messenger.domain.rx.SentMessageObservable
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Observable
import javax.inject.Inject

class ChatUseCase @Inject constructor():UseCase<QuerySnapshot, ChatUseCase.ChatParams>() {


    override fun buildUseCaseObservableUnit(params: ChatParams): Observable<Unit> {
        return SentMessageObservable(params.dialogId,params.message!!)
    }

    override fun buildUseCaseObservable(params: ChatParams): Observable<QuerySnapshot> {
        return FirestoreSnapshotObservable(FirebaseFirestore.getInstance()
                .collection("${params.dialogId}/messages")
                .orderBy("time", Query.Direction.DESCENDING)
                .limit(params.limit.toLong()))
    }

    fun getMessages(dialogId: String,observer: DefaultObserver<QuerySnapshot>,limit:Int){
        execute(ChatParams(1,dialogId,limit = limit),observer)
    }

    fun sentMessage(dialogId:String,message: Message){
        executeUnit(ChatParams(2,dialogId,message))
    }

    data class ChatParams(val type:Int,val dialogId: String,val message: Message? = null,val limit: Int = 0)


}