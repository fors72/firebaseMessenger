package com.firebase.messenger.presentation.di.module

import android.content.Context
import com.firebase.messenger.MessengerApplication
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule(private val context: MessengerApplication) {

    @Provides fun provideContext():Context = context
    @Provides fun provideFireBase():FirebaseAuth = FirebaseAuth.getInstance()
}