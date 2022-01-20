package com.a7medkenawy.elmarket.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.a7medkenawy.elmarket.databinding.ActivityLoginBinding
import android.view.Gravity

import android.R
import android.view.View

import android.widget.TextView

import android.view.ViewGroup


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginTvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.loginBtnLogin.setOnClickListener {



//            Toast.makeText(this@LoginActivity,"Hi Ahmed",Toast.LENGTH_LONG).show()
        }
    }
}