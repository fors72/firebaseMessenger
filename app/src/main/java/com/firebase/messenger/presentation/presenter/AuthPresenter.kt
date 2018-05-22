package com.firebase.messenger.presentation.presenter

import com.firebase.messenger.MessengerApplication
import com.firebase.messenger.data.model.User
import com.firebase.messenger.domain.interactor.AuthUseCase
import com.firebase.messenger.domain.rx.DefaultObserver
import com.firebase.messenger.presentation.view.impl.AuthView
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class AuthPresenter @Inject constructor(private val authUseCase: AuthUseCase):Presenter {


    lateinit var authView:AuthView

    fun auth(email:String,pass:String){
        authView.showLoading("Auth...")
        authUseCase.execute(FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass),AuthObserver())
    }

    fun auth(googleAccount: GoogleSignInAccount) {
        authView.showLoading("Auth with google...")
        authUseCase.execute( FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(googleAccount.idToken, null)),AuthObserver())
    }


    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
        authUseCase.dispose()
    }

    inner class AuthObserver: DefaultObserver<User?>() {

        override fun onNext(t: User?) {
            if (t!!.name == null){
                authView.createProfile()
            }else{
                authView.onSuccessAuth()
            }
        }

        override fun onComplete() {
            authView.hideLoading()
        }

        override fun onError(e: Throwable) {
            authView.hideLoading()
            authView.showError(e.message ?: "error")
        }

    }
}