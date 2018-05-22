package com.firebase.messenger.presentation.di.component

import com.firebase.messenger.presentation.di.module.DialogModule
import com.firebase.messenger.presentation.di.scope.PerActivity
import com.firebase.messenger.presentation.view.activity.ChatActivity
import com.firebase.messenger.presentation.view.fragment.DialogFragment
import com.firebase.messenger.presentation.view.fragment.UserListFragment
import dagger.Component


@PerActivity
@Component(dependencies = [ApplicationComponent::class],modules = [DialogModule::class])
interface ActivityComponent {
    fun inject(act:DialogFragment)
    fun inject(act:UserListFragment)
}