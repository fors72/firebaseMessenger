package com.firebase.messenger.data.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

data class Dialog(var name:String? = null,
                  @ServerTimestamp
                  var date: Date? = null,
                  var lastMessage: Message? = null,
                  var users: HashMap<String,Date>? = null):FirestoreModel()

data class DialogUpdate(var name:String? = null,
                        @ServerTimestamp
                        var date: Date? = null,
                        var users: HashMap<String,Any>? = null)



data class Message(var message:String? = null,
                   @ServerTimestamp
                   var time: Date? = null,
                   var userId: String? = null,
                   var readState:Int = 0):FirestoreModel()


@Parcelize data class User(var name:String? = null,
                           var photo:String? = null):FirestoreModel(), Parcelable





open class FirestoreModel{
    var documentPath:String? = null
    @Exclude get

}