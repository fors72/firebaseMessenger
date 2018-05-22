package com.firebase.messenger.presentation.view.activity

import android.os.Bundle
import com.firebase.messenger.presentation.navigation.Navigator
import com.google.firebase.auth.FirebaseAuth

class LaunchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isSignIn = FirebaseAuth.getInstance().currentUser != null
        if (isSignIn){
            Navigator.navigateToMain(this)
        }else{
            Navigator.navigateToAuth(this)
        }
        finish()
    }
}
