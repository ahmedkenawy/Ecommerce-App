package com.a7medkenawy.elmarket.ui.activities

import android.Manifest
import android.os.Bundle
import android.view.View
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityUserProfileBinding
import com.a7medkenawy.elmarket.models.User
import com.a7medkenawy.elmarket.utils.Constants
import android.content.pm.PackageManager
import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.bumptech.glide.Glide


class UserProfileActivity : BaseActivity(), View.OnClickListener {

    lateinit var user: User
    lateinit var binding: ActivityUserProfileBinding
    lateinit var userHasMap: HashMap<String, Any>
    private var selectedImage: Uri? = null
    private var profile_image: String? = null
    var tag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userHasMap = HashMap()


        if (intent.hasExtra(Constants.USER_DETAILS)) {
            user = intent.getParcelableExtra(Constants.USER_DETAILS)!!

        }



        handleUI()


    }

    private fun loadData(tag: Int) {
        if (tag == 1) {
            binding.ProfileEdFirstName.isEnabled = true
            binding.ProfileEdLastName.isEnabled = true
            binding.ProfilerEdEmail.isEnabled = true
        } else {
            Toast.makeText(this, "Yalaaahooy", Toast.LENGTH_LONG).show()

        }
    }

    private fun handleUI() {
        binding.ProfileEdFirstName.isEnabled = false
        binding.ProfileEdFirstName.setText(user.firstName)

        binding.ProfileEdLastName.isEnabled = false
        binding.ProfileEdLastName.setText(user.lastName)


        binding.ProfilerEdEmail.isEnabled = false
        binding.ProfilerEdEmail.setText(user.email)

        binding.profileImage.setOnClickListener(this)
        binding.ProfileBtnSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.profile_image -> {
                askForPermissions()
            }
            R.id.Profile_btn_save -> {
                if (selectedImage != null) {
                    FireStoreClass().uploadImageToCloudStorage(this, selectedImage!!)
                }
                handleUserUpdates()
            }
        }
    }

    private fun askForPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 1)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA
                ),
                101
            )
        }
    }

    private fun handleUserUpdates() {
        if (validateUserPhone()) {

            val mobileNumber = binding.ProfileEdPhone.text.toString().trim()
            if (mobileNumber.isNotEmpty()) {
                userHasMap["mobile"] = mobileNumber.toLong()
            }

            if (binding.rbMale.isChecked) {
                userHasMap["gender"] = "male"
            } else {
                userHasMap["gender"] = "female"
            }
            if (profile_image != null) {
                userHasMap["image"] = profile_image!!
            }

            if (mobileNumber.isNotEmpty() && profile_image != null) {
                userHasMap["completed"] = 1
            }

            FireStoreClass().updateUserDetails(this, userHasMap)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            101 -> if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Take Permission Successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Take Permission Failed", Toast.LENGTH_LONG).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            1 -> if (resultCode === RESULT_OK) {
                selectedImage = data?.data

                Glide
                    .with(this)
                    .load(selectedImage)
                    .centerCrop()
                    .placeholder(R.drawable.default_profile_image)
                    .into(binding.profileImage);


            }

        }
    }


    private fun validateUserPhone(): Boolean {
        return when {
            TextUtils.isEmpty(binding.ProfileEdPhone.text.toString().trim()) -> {
                showErrorSnackBar("please Enter Phone Number.", false)
                false
            }
            else -> {
                true
            }

        }
    }

    fun uploadProfileImage(imageProfile: String) {
        profile_image = imageProfile
        handleUserUpdates()
    }
}