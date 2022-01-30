package com.a7medkenawy.elmarket.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivitySettingsBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.User
import com.a7medkenawy.elmarket.utils.Constants
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() , View.OnClickListener {

    lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FireStoreClass().getUserDetails(this)

        binding.btnLogout.setOnClickListener(this)
        binding.tvEdit.setOnClickListener(this)

    }

    fun insertDataInViews(user: User) {
        binding.tvName.text = "${user.firstName} ${user.lastName}"
        binding.tvEmail.text=user.email
        binding.tvMobileNumber.text="0${user.mobile.toString()}"

        Glide.with(this)
            .load(user.image)
            .into(binding.ivUserPhoto)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_logout->{
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()

            }
            R.id.tv_edit->{
                val intent=Intent(this,UserProfileActivity::class.java)
                intent.putExtra(Constants.TAG,1)
                startActivity(intent)
                return
            }
        }
    }
}