package com.firebase.messenger.presentation.view.impl.base

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface RenderQueryListener {

    fun renderQuery(querySnapshot: QuerySnapshot)
    fun initQuery(documents: List<DocumentSnapshot>)
}