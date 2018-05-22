package com.firebase.messenger.domain.interactor


import com.firebase.messenger.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.Observer
import javax.inject.Inject

class AuthUseCase @Inject constructor(): UseCase<User?, Task<AuthResult>>() {


    override fun buildUseCaseObservable(params: Task<AuthResult>?): Observable<User?> {
        return AuthObservable(params!!)
    }

    inner class AuthObservable(private val task:Task<AuthResult>):Observable<User?>(){
        override fun subscribeActual(observer: Observer<in User?>) {
            task.addOnSuccessListener{
                FirebaseFirestore.getInstance().document("users/${it.user.uid}").get().addOnSuccessListener {
                    val user = if (it.exists()){
                        it.toObject(User::class.java)
                    }else{
                        User()
                    }
                    observer.onNext(user)
                    observer.onComplete()
                }.addOnFailureListener(observer::onError)
            }.addOnFailureListener(observer::onError)
        }
    }
}