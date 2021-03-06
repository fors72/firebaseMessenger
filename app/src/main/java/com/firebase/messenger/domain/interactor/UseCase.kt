package com.firebase.messenger.domain.interactor

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableObserver



abstract class UseCase<T, in P> {

    private var disposables :CompositeDisposable = CompositeDisposable()

    internal abstract fun buildUseCaseObservable(params: P): Observable<T>
    internal abstract fun buildUseCaseObservableUnit(params: P): Observable<Unit>

    fun execute(params: P,observer: DisposableObserver<T>) {
        val observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposable(observable.subscribeWith(observer))
    }

    fun executeUnit(params: P){
        val observable = this.buildUseCaseObservableUnit(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposable(observable.subscribe(Consumer {  }, Consumer { t -> t.printStackTrace() }))
    }


    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    fun clean() {
        disposables.clear()
    }


    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}