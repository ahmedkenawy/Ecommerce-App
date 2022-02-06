package com.a7medkenawy.elmarket.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityProductDetailsBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.utils.Constants
import com.bumptech.glide.Glide

class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBar()
        getProductDetails()
    }

    fun getProductDetails() {
        if (intent.hasExtra(Constants.PRODUCT_ID)) {
            val productId = intent.getStringExtra(Constants.PRODUCT_ID)
            FireStoreClass().getProductByID(this, productId!!)
        }
    }

    fun setProductDetails(product: Product) {
        Glide.with(this).load(product.image).into(binding.productImage)
        binding.productDetailsTitle.text = product.title
        binding.productDetailsPrice.text = "${product.price}$"
        binding.productDetailsDescription.text = product.description
        binding.productDetailsStockQuantity.text = product.stock_quantity

    }

    fun setToolBar() {
        setSupportActionBar(binding.productDetailsToolbar)
        val actionBar = supportActionBar
        actionBar?.title=""
        actionBar?.let {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_back)
            binding.productDetailsToolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }
}