package com.firebase.messenger.presentation.view.impl.base

interface LoadListView<in T> {

    fun renderObject(objects: Collection<T>)
    fun renderOldObjects(objects: Collection<T>)
    fun addNewObjects(objects: Collection<T>)


    fun showLoading()

    fun hideLoading()
}