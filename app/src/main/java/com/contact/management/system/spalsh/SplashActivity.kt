package com.contact.management.system.spalsh

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.contact.management.system.R
import com.contact.management.system.databinding.ActivitySplashBinding
import com.contact.management.system.util.goToHome
import com.contact.management.system.util.goToLogin
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), SplashListener.View {

    private lateinit var presenter: SplashListener.Presenter
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializa()
        Timer().schedule(object : TimerTask() {
            override fun run() {
                presenter.doCheckExistingUser()
            }
        }, 2000)


    }

    private fun initializa() {

        presenter = SplashPresenter(this,this@SplashActivity)
    }

    override fun onMoveHome() {
        goToHome()
        finish()
    }

    override fun onMoveLogin() {
        goToLogin()
        finish()
    }
}