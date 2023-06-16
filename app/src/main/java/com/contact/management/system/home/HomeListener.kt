package com.contact.management.system.home

import com.contact.management.system.model.UserContact

interface HomeListener {

    interface View {
        fun onSuccess(message:String)
        fun onMoveLogin()
        fun reLoad(list : List<UserContact>)
    }

    interface Presenter {
        fun doLogout()
        fun fetchData()
        fun onUpdateContact(list: List<UserContact>, data: UserContact)
    }

}