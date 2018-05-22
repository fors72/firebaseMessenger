package com.firebase.messenger.presentation.di

import com.firebase.messenger.MessengerApplication
import com.firebase.messenger.presentation.di.component.*
import com.firebase.messenger.presentation.di.module.ApplicationModule
import com.firebase.messenger.presentation.di.module.DialogModule

object Injector {
    public lateinit var appComponent: ApplicationComponent
    private var dialogComponent: ActivityComponent? = null
    private var chatComponent: ChatComponent? = null

//    public var dialogComponent: Lazy<ActivityComponent> = lazy { DaggerDialogComponent.builder().applicationComponent(appComponent).build() }

    fun init(context: MessengerApplication){
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(context))
                .build()
    }

    fun getActivityComponent():ActivityComponent{
        return if (dialogComponent == null){
            dialogComponent = DaggerActivityComponent.builder().applicationComponent(appComponent).dialogModule(DialogModule()).build()
            dialogComponent!!
        }else{
            dialogComponent!!
        }
    }

    fun clearActivityComponent(){
        dialogComponent = null
    }
    fun getChatComponent():ChatComponent{
        return if (chatComponent == null){
            chatComponent = DaggerChatComponent.builder().applicationComponent(appComponent).build()
            chatComponent!!
        }else{
            chatComponent!!
        }
    }

    fun clearChatComponent(){
        chatComponent = null
    }





}