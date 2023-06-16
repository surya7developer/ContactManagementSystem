package com.contact.management.system.spalsh

import com.contact.management.system.util.getRoomObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashPresenter(val view: SplashListener.View,val context: SplashActivity) : SplashListener.Presenter {

    override fun doCheckExistingUser() {
        GlobalScope.launch {
            val user = context.getRoomObject().DoaService().getUser()
            if (user?.email != null &&  user.email.isNotEmpty()){
                if (user.isLogin == 0){
                    view.onMoveLogin()
                }else {
                    view.onMoveHome()
                }
            }else {
                view.onMoveLogin()
            }
        }
    }
}