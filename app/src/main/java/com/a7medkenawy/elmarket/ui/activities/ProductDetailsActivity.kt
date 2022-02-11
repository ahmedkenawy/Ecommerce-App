package com.a7medkenawy.elmarket.ui.activities

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityProductDetailsBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Cart
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.utils.Constants
import com.bumptech.glide.Glide

class ProductDetailsActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityProductDetailsBinding
    private var mProduct: Product? = null

    private var productId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBar()
        getProductDetails()
        checkIfProductExist()

        binding.productDetailsGoToCart.setOnClickListener(this)
        binding.productDetailsAddToCart.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.product_details_add_to_cart -> {
                addToCart()
            }
        }
    }

    private fun getProductDetails() {
        if (intent.hasExtra(Constants.PRODUCT_ID)) {
            productId = intent.getStringExtra(Constants.PRODUCT_ID)
            FireStoreClass().getProductByID(this, productId!!)
        }
        checkIfCurrentUser()
    }

    private fun checkIfCurrentUser() {

        if (intent.hasExtra(Constants.USER_ID)) {
            val userId = intent.getStringExtra(Constants.USER_ID)
            if (FireStoreClass().getCurrentUser() == userId) {
                binding.productDetailsAddToCart.visibility = View.GONE
                binding.productDetailsGoToCart.visibility = View.GONE
            } else {
                binding.productDetailsAddToCart.visibility = View.VISIBLE

            }
        }
    }

    fun setProductDetails(product: Product) {
        mProduct = product
        Glide.with(this).load(product.image).into(binding.productImage)
        binding.productDetailsTitle.text = product.title
        binding.productDetailsPrice.text = "${product.price}$"
        binding.productDetailsDescription.text = product.description
        binding.productDetailsStockQuantity.text = product.stock_quantity

        if (binding.productDetailsStockQuantity.text == "0") {
            binding.productDetailsAddToCart.setBackgroundResource(R.drawable.signin_background)
            binding.productDetailsAddToCart.setTextColor(ContextCompat.getColor(this,R.color.black))
            binding.productDetailsAddToCart.text = resources.getString(R.string.out_of_stock)
            binding.productDetailsGoToCart.visibility=View.GONE
        }


    }

    fun setToolBar() {
        setSupportActionBar(binding.productDetailsToolbar)
        val actionBar = supportActionBar
        actionBar?.title = ""
        actionBar?.let {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_back)
            binding.productDetailsToolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    fun addToCart() {
        showProgressDialog()
        val cart = Cart(
            user_id = FireStoreClass().getCurrentUser(),
            product_id = productId!!,
            title = mProduct!!.title,
            price = mProduct!!.price,
            image = mProduct!!.image,
            cart_quantity = Constants.CART_QUANTITY,
            stock_quantity = mProduct!!.stock_quantity,
        )
        FireStoreClass().addToCarts(this, cart)
    }

    fun cartAddedSuccessfully() {
        hideProgressDialog()
        showCustomToast()
        binding.productDetailsAddToCart.visibility = View.GONE
        binding.productDetailsGoToCart.visibility = View.VISIBLE
    }


    fun productExist() {
        binding.productDetailsAddToCart.visibility = View.GONE
        binding.productDetailsGoToCart.visibility = View.VISIBLE
    }

    private fun checkIfProductExist() {
        FireStoreClass().checkIfProductExist(this, productId!!)
    }


}