package com.firebase.messenger.domain.rx

import io.reactivex.observers.DisposableObserver

open class DefaultObserver<T>: DisposableObserver<T>() {
    override fun onComplete() {
        //default
    }

    override fun onNext(t: T) {
        //default
    }

    override fun onError(e: Throwable) {
        //default
    }
}