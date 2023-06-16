package com.contact.management.system.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.contact.management.system.home.HomeActivity
import com.contact.management.system.login.LoginActivity
import com.contact.management.system.room.ContactDatabase


var toast: Toast? = null

fun Context.showLog(tag:String, msg:String){
    Log.d(tag,msg)
}

fun Context.showToast(msg:String){
    Handler(Looper.getMainLooper()).post {
        toast?.cancel();
        toast = Toast(applicationContext)
        toast?.setText(msg)
        toast?.duration = Toast.LENGTH_SHORT
        toast?.show()
    }
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun Context.goToHome(){
    val intent = Intent(this, HomeActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
    (this as Activity).finish()

}

fun Context.goToLogin(){
    val intent = Intent(this, LoginActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
    (this as Activity).finish()

}

fun isValidEmail(target: CharSequence?): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
}


fun Context.getRoomObject(): ContactDatabase {
    return ContactDatabase.getDatabase(this)
}
