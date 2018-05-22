package com.firebase.messenger.presentation.di.component

import android.content.Context
import com.firebase.messenger.MessengerApplication
import com.firebase.messenger.presentation.di.module.ApplicationModule
import com.firebase.messenger.presentation.view.activity.AuthActivity
import com.firebase.messenger.presentation.view.activity.MainActivity

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(app: MessengerApplication)
    fun inject(app: MainActivity)
    fun inject(app: AuthActivity)

    fun context():Context
}