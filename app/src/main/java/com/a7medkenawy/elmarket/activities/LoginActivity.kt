package com.a7medkenawy.elmarket.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.a7medkenawy.elmarket.databinding.ActivityLoginBinding
import android.view.Gravity

import android.R
import android.text.TextUtils
import android.view.View

import android.widget.TextView

import android.view.ViewGroup
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginTvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.LoginForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.loginBtnLogin.setOnClickListener {

            signInWithEmailAndPassword()
//            Toast.makeText(this@LoginActivity,"Hi Ahmed",Toast.LENGTH_LONG).show()
        }
    }

    private fun validateLoginEditText(): Boolean {
        return when {
            TextUtils.isEmpty(binding.loginEdEmail.text.toString().trim()) -> {
                showErrorSnackBar("please enter email.", false)
                false
            }

            TextUtils.isEmpty(binding.loginEdEmail.text.toString().trim()) -> {
                showErrorSnackBar("please enter password.", false)
                false
            }

            else -> {
                showCustomToast()
                true
            }
        }
    }

    private fun signInWithEmailAndPassword() {
        if (validateLoginEditText()) {

            var email = binding.loginEdEmail.text.toString().trim()
            var password = binding.loginEdPassword.text.toString().trim()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var firebaseUser = task.result.user
                        FireStoreClass().getUserDetails(this)
                        showCustomToast()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }

                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
        }
    }
}