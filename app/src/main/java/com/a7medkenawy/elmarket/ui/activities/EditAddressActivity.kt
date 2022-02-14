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
import com.a7medkenawy.elmarket.utils.Constants

class EditAddressActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEditAddressBinding
    private var mAddress: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.addressSaveAddressButton.setOnClickListener(this)

        if (intent.hasExtra(Constants.AddressDetails)) {
            mAddress = intent.getParcelableExtra(Constants.AddressDetails)
            if (mAddress!!.id.isNotEmpty()) {
                binding.addressAddAddressTv.text = resources.getString(R.string.update_address)
                binding.addressSaveAddressButton.text = resources.getString(R.string.update_address)

                binding.addressFullNameEt.setText(mAddress!!.name)
                binding.addressPhoneNumberEt.setText(mAddress!!.mobileNumber)
                binding.addressAddressEt.setText(mAddress!!.address)
                binding.addressZipCodeEt.setText(mAddress!!.zipCode)
                binding.addressAdditionalNoteEt.setText(mAddress!!.additionalNote)

                when (mAddress!!.type) {
                    "Home" -> {
                        binding.addressRbHome.isChecked = true
                    }
                    "Office" -> {
                        binding.addressRbOffice.isChecked = true
                    }
                    "Other" -> {
                        binding.addressRbOther.isChecked = true
                        binding.addressOtherNote.visibility = View.VISIBLE
                        binding.addressOtherNoteEt.setText(mAddress!!.otherDetails)
                    }
                }
            }
        }



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
        actionBar?.title = ""
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
            if (mAddress != null && mAddress!!.id.isNotEmpty()) {
                showProgressDialog()
                FireStoreClass().updateAddress(this,address,mAddress!!.id)
            } else {
                FireStoreClass().addAddressToDatabase(this, address)
            }
        }

    }



    fun backToAddressActivity() {
        setResult(RESULT_OK)
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