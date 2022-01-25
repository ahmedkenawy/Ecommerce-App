package com.a7medkenawy.elmarket.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityUserProfileBinding
import com.a7medkenawy.elmarket.models.User
import com.a7medkenawy.elmarket.utils.Constants

class UserProfileActivity : AppCompatActivity() {
    lateinit var user: User
    lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.USER_DETAILS)) {
            user = intent.getParcelableExtra(Constants.USER_DETAILS)!!
        }

        binding.ProfileEdFirstName.isEnabled = false
        binding.ProfileEdFirstName.setText(user.firstName)

        binding.ProfileEdLastName.isEnabled = false
        binding.ProfileEdLastName.setText(user.lastName)


        binding.ProfilerEdEmail.isEnabled = false
        binding.ProfilerEdEmail.setText(user.email)


    }
}