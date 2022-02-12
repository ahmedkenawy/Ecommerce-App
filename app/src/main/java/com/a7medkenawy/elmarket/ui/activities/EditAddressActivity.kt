package com.a7medkenawy.elmarket.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityEditAddressBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Address

class EditAddressActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEditAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.addressSaveAddressButton.setOnClickListener(this)

        binding.addressRadioButton.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.address_rb_other) {
                binding.addressOtherNote.visibility = View.VISIBLE
            } else {
                binding.addressOtherNote.visibility = View.GONE
            }
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.addressToolbar)
        val actionBar = supportActionBar
        actionBar?.title=""
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic__back)
        }

        binding.addressToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.address_saveAddress_button -> {
                showProgressDialog()
                addAddress()
            }

        }
    }


    private fun addAddress() {


        if (validateViews()) {

            val address = Address(
                user_id = FireStoreClass().getCurrentUser(),
                name = binding.addressFullNameEt.text.toString().trim(),
                mobileNumber = binding.addressPhoneNumberEt.text.toString().trim(),
                address = binding.addressAddressEt.text.toString().trim(),
                zipCode = binding.addressZipCodeEt.text.toString().trim(),
                additionalNote = binding.addressAdditionalNoteEt.text.toString().trim(),
                type = getType(),
                otherDetails = binding.addressOtherNoteEt.text.toString().trim(),
                id = ""
            )

            FireStoreClass().addAddressToDatabase(this, address)
        }

    }

    fun backToAddressActivity() {
        val intent = Intent(this, AddressActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun getType(): String {
        var type = when {
            binding.addressRbHome.isChecked -> {
                "Home"
            }
            binding.addressRbOffice.isChecked -> {
                "Office"
            }

            else -> {
                "Other"
            }
        }
        return type
    }

    private fun validateViews(): Boolean {
        return when {
            TextUtils.isEmpty(binding.addressFullNameEt.text.toString().trim()) -> {
                showErrorSnackBar("please enter your full name.", false)
                false
            }
            TextUtils.isEmpty(binding.addressPhoneNumberEt.text.toString().trim()) -> {
                showErrorSnackBar("please enter your phone number.", false)
                false
            }
            TextUtils.isEmpty(binding.addressAddressEt.text.toString().trim()) -> {
                showErrorSnackBar("please enter your address.", false)
                false
            }
            TextUtils.isEmpty(binding.addressAddressEt.text.toString().trim()) -> {
                showErrorSnackBar("please enter your zip code.", false)
                false
            }
            TextUtils.isEmpty(binding.addressAdditionalNoteEt.text.toString().trim()) -> {
                showErrorSnackBar("please enter additional note", false)
                false
            }
            else -> {
                true
            }
        }
    }
}