package com.firebase.messenger

import com.firebase.messenger.data.model.Dialog
import com.firebase.messenger.data.model.FirestoreModel
import com.firebase.messenger.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import kotlin.reflect.KClass

fun <T : FirestoreModel> List<DocumentSnapshot>.toModel(kClass: KClass<T>):List<T>{
    return map { it.toObject(kClass.java).also { obj -> obj!!.documentPath = it.reference.path }!! }
}




fun Dialog.toUpdateModel(): MutableMap<String, Any?> {
    val usersNew = mutableMapOf<String,Any?>()
    users?.forEach { user, _ -> usersNew[user] = FieldValue.serverTimestamp() }
    val message = mutableMapOf<String,Any?>()
    message["message"] = lastMessage!!.message
    message["time"] = FieldValue.serverTimestamp()
    return mutableMapOf("name" to name as Any?,"date" to date as Any?,"lastMessage" to message as Any?,"users" to usersNew)
}


fun User.dialogPathWithMe():String{
    val myId = FirebaseAuth.getInstance().currentUser!!.uid
    val uid = documentPath!!.replace("users/","")
    val arr = arrayOf(myId,uid)
    arr.sort()
    return arr[0] + arr[1]
}