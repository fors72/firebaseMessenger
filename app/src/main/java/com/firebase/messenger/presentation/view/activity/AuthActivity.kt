package com.firebase.messenger.presentation.view.activity

import android.app.ProgressDialog
import android.os.Bundle
import com.firebase.messenger.R
import com.firebase.messenger.presentation.presenter.AuthPresenter
import com.firebase.messenger.presentation.view.impl.AuthView
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog
import javax.inject.Inject
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.Auth
import android.content.Intent
import com.firebase.messenger.presentation.navigation.Navigator


class AuthActivity : BaseActivity(),AuthView {



    @Inject
    lateinit var authPresenter: AuthPresenter


    private var mGoogleApiClient: GoogleApiClient? = null

    var progressAllert: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        getApplicationComponent().inject(this)
        authPresenter.authView = this
        passAuthBtn.setOnClickListener{
            if (emailField.text.isEmpty()){
                showError("email field empty")
                return@setOnClickListener
            }
            if (passField.text.isEmpty()){
                showError("pass field empty")
                return@setOnClickListener
            }
            authPresenter.auth(emailField.text.toString(),passField.text.toString())
        }
        googleAuthbtn.setOnClickListener{
            if (mGoogleApiClient == null) {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                mGoogleApiClient = GoogleApiClient.Builder(this@AuthActivity.applicationContext)
                        .enableAutoManage(this@AuthActivity, null)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build()
            }
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, 9001)
        }

        createNewTxt.setOnClickListener{
            alert("not support yet"){
                positiveButton("OK"){dismiss()}
            }.show()
        }
    }

    override fun onSuccessAuth() {
        Navigator.navigateToMain(this)
    }

    override fun createProfile() {
        Navigator.navigateToNewProfile(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9001) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount!!
                authPresenter.auth(account)
            }
        }
    }

    override fun showLoading(message: String) {
        progressAllert = this.indeterminateProgressDialog(message){
        }
    }

    override fun hideLoading() {
        progressAllert?.dismiss()
        progressAllert = null
    }

    override fun onDestroy() {
        super.onDestroy()
        authPresenter.destroy()
    }

    override fun showError(message: String) {
        alert(message){
            positiveButton("OK"){dismiss()}
        }.show()
        passField.text.clear()
    }
}
