package com.a7medkenawy.elmarket.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityMainBinding
import com.a7medkenawy.elmarket.utils.Constants

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(
            Constants.SharedPreferencesName,
            Context.MODE_PRIVATE
        )
        val username = sharedPreferences.getString(Constants.userName, "No User Name")
        binding.myTV.text = username
    }
}