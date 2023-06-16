package com.contact.management.system.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.contact.management.system.databinding.ActivityLoginBinding
import com.contact.management.system.signup.SignUpActivity
import com.contact.management.system.util.goToHome
import com.contact.management.system.util.isValidEmail
import com.contact.management.system.util.showToast

class LoginActivity : AppCompatActivity(),LoginContact.View {

    private lateinit var binding: ActivityLoginBinding
    lateinit var presenter : LoginContact.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
        eventManagment()
    }

    private fun eventManagment() {
        binding.apply {

            btnSignUp.setOnClickListener {
                        startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
            }

            btnSignIn.setOnClickListener {

                val email = edtEmail.text.toString().trim()
                val pass = edtPassword.text.toString().trim()

                if (email.isEmpty()) {
                    showToast("Please enter email")
                } else if (!isValidEmail(email)) {
                    showToast("Please enter valid email")
                } else if (pass.isEmpty()) {
                    showToast("Please enter password")
                } else {
                    presenter.doLogin(email,pass)
                }
            }

        }
    }

    private fun initialize() {
        presenter = LoginPresenter(this,this@LoginActivity)

    }

    override fun onSuccess(message: String) {
        showToast(message)
    }

    override fun onError(message: String) {
        showToast(message)
    }

    override fun moveToHome() {
        goToHome()
        finish()
    }


}