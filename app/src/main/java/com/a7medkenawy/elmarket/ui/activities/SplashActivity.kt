package com.a7medkenawy.elmarket.ui.activities

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.os.Handler
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.ui.activities.onboardingscreen.OnBoardingScreen
import com.google.firebase.auth.FirebaseAuth


class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        if (isNotFirstTime()) {
            Handler().postDelayed({
                val currentUserID = FireStoreClass().getCurrentUser()
                if (currentUserID.isNotEmpty()) {
                    startActivity(Intent(this@SplashActivity, DashBoardActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                finish()
            }, 2500)

//        } else {
//            Handler().postDelayed(
//                {
//                    startActivity(Intent(this@SplashActivity, OnBoardingScreen::class.java))
//                    finish()
//                },
//                2500
//            )
//        }
    }

    private fun isNotFirstTime(): Boolean {
        val sharedPreferences = getSharedPreferences("OnBoardingScreen", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("onBoarding_Key", false)
    }

}