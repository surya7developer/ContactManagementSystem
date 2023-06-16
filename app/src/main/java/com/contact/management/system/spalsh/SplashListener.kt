package com.contact.management.system.spalsh

interface SplashListener {

    interface View {
        fun onMoveHome()
        fun onMoveLogin()
    }

    interface Presenter {
        fun doCheckExistingUser()
    }

}