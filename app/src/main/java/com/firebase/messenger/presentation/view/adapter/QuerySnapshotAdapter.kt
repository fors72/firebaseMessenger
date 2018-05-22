package com.firebase.messenger.presentation.view.adapter

import android.content.Context
import com.firebase.messenger.data.model.FirestoreModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.lang.reflect.ParameterizedType

abstract class QuerySnapshotAdapter<T: FirestoreModel>(context: Context, list:List<T>): SRAdapter<T>(context,list) {


    public fun renderQuery(querySnapshot: QuerySnapshot){
        fun getObjectWithPath(doc: QueryDocumentSnapshot,cl:Class<T>) = doc.toObject(cl).apply { documentPath = doc.reference.path }
        val eClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        querySnapshot.documentChanges.forEach {
            when (it.type){
                DocumentChange.Type.ADDED -> {
                    addObject(it.newIndex,getObjectWithPath(it.document,eClass))
                    if (it.newIndex == 0)recyclerView.scrollToPosition(0)
                }
                DocumentChange.Type.MODIFIED -> {
                    if (it.oldIndex != it.newIndex){
                        moveObject(it.oldIndex,it.newIndex)
                        recyclerView.scrollToPosition(0)
                    }
                    updateObject(it.newIndex,getObjectWithPath(it.document,eClass))
                }
                DocumentChange.Type.REMOVED -> removeObject(it.oldIndex)
            }
        }

    }
}