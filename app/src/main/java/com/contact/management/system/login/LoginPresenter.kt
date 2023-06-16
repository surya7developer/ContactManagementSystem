package com.contact.management.system.login

import com.contact.management.system.model.User
import com.contact.management.system.util.getRoomObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginPresenter(var view: LoginContact.View,val context: LoginActivity) : LoginContact.Presenter {

    override fun doLogin(email: String, pass: String) {

        GlobalScope.launch {
            val database = context.getRoomObject()
            val user = database.DoaService().getUser()
            if (user?.email != null &&  user.email.isNotEmpty()){

                if (user.email == email && user.pass == pass){

                    var updateUser = User(
                        id = user.id,
                        fName = user.fName,
                        lName = user.lName,
                        mobile = user.mobile,
                        email = user.email,
                        pass = user.pass,
                        isLogin = 1
                    )

                    database.DoaService().updateUser(updateUser)
                    view.onSuccess("Login Success")
                    view.moveToHome()
                }else {
                    view.onError("Please enter valid email and password")
                }

            }else {
                view.onError("User not found")
            }
        }
    }


}