package com.a7medkenawy.elmarket.ui.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.os.Handler
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.google.firebase.auth.FirebaseAuth


class SplashActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                val currentUserID = FireStoreClass().getCurrentUser()
                if (currentUserID.isNotEmpty()) {
                    startActivity(Intent(this@SplashActivity, DashBoardActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                finish()
            },
            2500
        )
    }

    }