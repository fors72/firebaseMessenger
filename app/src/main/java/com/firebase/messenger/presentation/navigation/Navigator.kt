package com.firebase.messenger.presentation.navigation

import android.content.Context
import android.content.Intent
import com.firebase.messenger.presentation.view.activity.AuthActivity
import com.firebase.messenger.presentation.view.activity.ChatActivity
import com.firebase.messenger.presentation.view.activity.CreateProfileActivity
import com.firebase.messenger.presentation.view.activity.MainActivity
import org.jetbrains.anko.startActivity

object Navigator {


    fun navigateToAuth(context:Context){
        context.startActivity(Intent(context,AuthActivity::class.java))
    }
    fun navigateToMain(context:Context){
        context.startActivity<MainActivity>()
    }


    fun navigateToChat(context:Context,dialogPath:String){
        context.startActivity<ChatActivity>("dialogId" to dialogPath)
    }

    fun navigateToContact(context:Context){

    }
    fun navigateToUserProfile(context:Context,userId:Int){
    }
    fun navigateToNewProfile(context:Context){
        context.startActivity<CreateProfileActivity>()
    }
}