package com.a7medkenawy.elmarket.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.ForgetBtnReset.setOnClickListener {
            handleResetEmail()
        }
    }

    private fun handleResetEmail() {
        val email = binding.ResetEdEmail.text.toString().trim()
        if (email.isEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Email Sent", Toast.LENGTH_LONG).show()
                }
            }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
        }
    }
}