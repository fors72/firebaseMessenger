package com.firebase.messenger.presentation.di.component

import com.firebase.messenger.presentation.di.scope.ChatScope
import com.firebase.messenger.presentation.view.activity.ChatActivity
import dagger.Component


@ChatScope
@Component(dependencies = [ApplicationComponent::class])
interface ChatComponent {
    fun inject(act: ChatActivity)
}