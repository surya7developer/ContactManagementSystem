package com.contact.management.system.signup

import androidx.lifecycle.MutableLiveData
import com.contact.management.system.model.User

interface SignUpContact {

    interface View {
        fun onSuccess(message:String)
        fun onError(message:String)
    }

    interface Presenter {
        fun doSignup(user: User)
    }

}