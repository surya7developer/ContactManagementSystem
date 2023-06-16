package com.contact.management.system.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.contact.management.system.databinding.ActivitySignUpBinding
import com.contact.management.system.model.User
import com.contact.management.system.util.goToHome
import com.contact.management.system.util.isValidEmail
import com.contact.management.system.util.showToast

class SignUpActivity : AppCompatActivity(), SignUpContact.View {

    private lateinit var binding: ActivitySignUpBinding
    lateinit var presenter: SignUpContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
        eventManagement()

    }

    private fun eventManagement() {
        binding.apply {

            btnSignIn.setOnClickListener {
                onBackPressed()
            }

            btnSignUp.setOnClickListener {

                val fName = edtfName.text.toString().trim()
                val lName = edtlName.text.toString().trim()
                val mobile = edtMobile.text.toString().trim()
                val email = edtEmail.text.toString().trim()
                val pass = edtPassword.text.toString().trim()


                if (fName.isEmpty()) {
                    showToast("Please enter first name")
                } else if (lName.isEmpty()) {
                    showToast("Please enter last name")
                } else if (mobile.isEmpty()) {
                    showToast("Please enter mobile")
                } else if (email.isEmpty()) {
                    showToast("Please enter email")
                } else if (!isValidEmail(email)) {
                    showToast("Please enter valid email")
                } else if (pass.isEmpty()) {
                    showToast("Please enter password")
                } else {

                    presenter.doSignup(
                        User(
                            id = 0,
                            fName = fName,
                            lName = lName,
                            email = email,
                            mobile = mobile,
                            pass = pass,
                            isLogin = 1
                        )
                    )
                }
            }

        }

    }

    private fun initialize() {
        presenter = SignUpPresenter(this, this@SignUpActivity)

    }

    override fun onSuccess(message: String) {
        showToast(message)
        goToHome()

    }

    override fun onError(message: String) {

    }
}