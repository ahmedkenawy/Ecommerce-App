package com.a7medkenawy.elmarket.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityAddProductBinding
import androidx.core.app.ActivityCompat.startActivityForResult
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.utils.Constants
import com.bumptech.glide.Glide


class AddProductActivity : BaseActivity(), View.OnClickListener {

    var selectedImage: Uri? = null

    lateinit var binding: ActivityAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar()

        binding.addProductIv.setOnClickListener(this)
        binding.addProductSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_product_iv -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 1)
            }

            R.id.add_product_submit -> {
                if (validateViews()) {
                    if (selectedImage != null) {
                        FireStoreClass().uploadImageToCloudStorage(this, selectedImage!!, Constants.PRODUCT_IMAGE)
                        showCustomToast()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectedImage = data?.data

            Glide.with(this)
                .load(selectedImage)
                .into(binding.addProductDisplayImage)
        }

    }

    private fun supportActionBar() {
        setSupportActionBar(binding.addProductToolbar)


        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = ""
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_back)
        }
    }

    private fun validateViews(): Boolean {
        return when {
            selectedImage == null -> {
                showErrorSnackBar("please choose product Image", true)
                false
            }
            TextUtils.isEmpty(binding.addProductTvProductTitle.text.toString().trim()) -> {
                showErrorSnackBar("please enter product title", true)
                false
            }

            TextUtils.isEmpty(binding.addProductTvProductPrice.text.toString().trim()) -> {
                showErrorSnackBar("please enter product price", true)
                false
            }

            TextUtils.isEmpty(binding.addProductTvDescription.text.toString().trim()) -> {
                showErrorSnackBar("please enter product Description", true)
                false
            }

            TextUtils.isEmpty(binding.addProductTvProductQuantity.text.toString().trim()) -> {
                showErrorSnackBar("please enter product Quantity", true)
                false
            }
            else -> true

        }
    }

    fun uploadProductImage(imageUri: Uri) {
        selectedImage = imageUri

    }
}