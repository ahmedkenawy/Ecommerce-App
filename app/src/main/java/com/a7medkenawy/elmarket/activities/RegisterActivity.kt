package com.a7medkenawy.elmarket.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityRegisterBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        binding.RegisterBtnLogin.setOnClickListener {

            registerNewUser()

        }

        binding.RegisterTvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun registerNewUser() {
        if (validateRegisterDetails()) {

            var email = binding.RegisterEdEmail.text.toString().trim()
            var password = binding.RegisterEdPassword.text.toString().trim()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var firebaseUser = task.result.user
                        val user = User(
                            firebaseUser?.uid,
                            binding.RegisterEdFirstName.text.toString(),
                            binding.RegisterEdLastName.text.toString(),
                            binding.RegisterEdEmail.text.toString()
                        )
                        FireStoreClass().registerUser(this@RegisterActivity, user)
                        showCustomToast()
                    }

                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarRegisterActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic__back)
        }

        binding.toolbarRegisterActivity.setNavigationOnClickListener { onBackPressed() }
    }


    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.RegisterEdFirstName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(binding.RegisterEdLastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(binding.RegisterEdEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(binding.RegisterEdPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(
                binding.RegisterEdConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }

            binding.RegisterEdPassword.text.toString()
                .trim { it <= ' ' } != binding.RegisterEdConfirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !binding.cbTermsAndCondition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_agree_terms_and_condition),
                    true
                )
                false
            }
            else -> {

                true
            }

        }
    }
}