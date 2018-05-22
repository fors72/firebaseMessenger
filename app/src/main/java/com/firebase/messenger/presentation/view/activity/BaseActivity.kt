package com.firebase.messenger.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebase.messenger.MessengerApplication
import com.firebase.messenger.presentation.di.Injector
import com.firebase.messenger.presentation.di.component.ApplicationComponent
import com.firebase.messenger.presentation.navigation.Navigator
import javax.inject.Inject


abstract class BaseActivity:AppCompatActivity() {



    protected fun getApplicationComponent(): ApplicationComponent {
        return Injector.appComponent
    }
}