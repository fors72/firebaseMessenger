package com.firebase.messenger

import com.firebase.messenger.data.model.FirestoreModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlin.reflect.KClass

fun <T : FirestoreModel> List<DocumentSnapshot>.toModel(kClass: KClass<T>):List<T>{
    return map { it.toObject(kClass.java).also { obj -> obj!!.documentPath = it.reference.path }!! }
}