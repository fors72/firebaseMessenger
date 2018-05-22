package com.firebase.messenger

import android.app.Application
import com.firebase.messenger.presentation.di.Injector
import com.firebase.messenger.presentation.di.component.ApplicationComponent
import com.firebase.messenger.presentation.di.component.DaggerApplicationComponent
import com.firebase.messenger.presentation.di.module.ApplicationModule
import javax.inject.Inject

class MessengerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }




}