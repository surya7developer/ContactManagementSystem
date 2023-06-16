package com.contact.management.system.signup

import com.contact.management.system.model.User
import com.contact.management.system.util.getRoomObject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpPresenter(var view: SignUpContact.View,val context: SignUpActivity) : SignUpContact.Presenter {


    @OptIn(DelicateCoroutinesApi::class)
    override fun doSignup(user: User) {

        var job = GlobalScope.launch {
            val database = context.getRoomObject()
            database.DoaService().addUser(user)
        }
        view.onSuccess("Register successfully")


    }


}