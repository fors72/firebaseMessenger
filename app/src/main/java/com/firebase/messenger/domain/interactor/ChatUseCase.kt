package com.firebase.messenger.domain.interactor

import com.firebase.messenger.data.model.Message
import com.firebase.messenger.domain.rx.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Observable
import javax.inject.Inject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class ChatUseCase @Inject constructor():UseCase<QuerySnapshot, ChatUseCase.ChatParams>() {


    private val updateSubject:Lazy<PublishSubject<Unit>> = lazy { PublishSubject.create<Unit>() }


    lateinit var messageObserver: DefaultObserver<QuerySnapshot>


    override fun buildUseCaseObservableUnit(params: ChatParams): Observable<Unit> {
        val dialogId = params.dialogId
        val message = params.message!!
        return SentMessage(dialogId,message)
                .onErrorResumeNext(CreateUserDialog(dialogId,message)
                        .flatMap { SentMessage(dialogId,message) }
                        .doOnNext { if (updateSubject.isInitialized())updateSubject.value.onNext(Unit) })
                .flatMap { UpdateDialog(dialogId,message) }

    }

    override fun buildUseCaseObservable(params: ChatParams): Observable<QuerySnapshot> {
        return FirestoreSnapshotObservable(FirebaseFirestore.getInstance()
                .collection("${params.dialogId}/messages")
                .orderBy("time", Query.Direction.DESCENDING)
                .limit(params.limit.toLong())).cache()
//                .repeatWhen { t -> t.delay(1,TimeUnit.SECONDS) }
                .retryWhen {t -> t.flatMap { _ -> updateSubject.value } }

    }

    fun getMessages(dialogId: String,observer: DefaultObserver<QuerySnapshot>,limit:Int){
        execute(ChatParams(1,dialogId,limit = limit),observer)
    }

    fun sentMessage(dialogId:String,message: Message){
        executeUnit(ChatParams(2,dialogId,message))
    }

    data class ChatParams(val type:Int,val dialogId: String,val message: Message? = null,val limit: Int = 0)


}