package com.contact.management.system.login

interface LoginContact {

    interface View {
        fun onSuccess(message:String)
        fun onError(message:String)
        fun moveToHome()
    }

    interface Presenter {
        fun doLogin(email:String,pass:String)
    }

}