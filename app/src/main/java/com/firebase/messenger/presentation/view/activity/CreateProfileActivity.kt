package com.firebase.messenger.presentation.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.messenger.R
import com.firebase.messenger.data.model.User
import com.firebase.messenger.presentation.navigation.Navigator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_profile.*
import org.jetbrains.anko.toast

class CreateProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        btnCreateProfile.setOnClickListener{
            FirebaseFirestore.getInstance().document("users/${FirebaseAuth.getInstance().currentUser!!.uid}").set(User(nameField.text.toString())).addOnSuccessListener {
                Navigator.navigateToMain(this)
            }.addOnFailureListener {
                toast("error")
            }
        }
    }
}
