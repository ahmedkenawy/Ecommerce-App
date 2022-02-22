package com.a7medkenawy.elmarket.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityCheckoutBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Address
import com.a7medkenawy.elmarket.models.Cart
import com.a7medkenawy.elmarket.models.Order
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.ui.adapter.CartAdapter
import com.a7medkenawy.elmarket.utils.Constants

class ActivityCheckout : BaseActivity() {
    var mAddressSelected: Address? = null
    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartsList: ArrayList<Cart>
    private var mSubTotal: Double = 0.0
    private var mTotalAmount: Double = 0.0

    lateinit var binding: ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar()

        if (intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)) {
            mAddressSelected = intent.getParcelableExtra(Constants.EXTRA_SELECTED_ADDRESS)
        }

        if (mAddressSelected != null) {
            binding.tvCheckoutFullName.text = mAddressSelected!!.name
            binding.tvCheckoutAddress.text =
                "${mAddressSelected!!.address}, ${mAddressSelected!!.zipCode}"
            binding.tvCheckoutAddressType.text = mAddressSelected!!.type
            binding.tvCheckoutAdditionalNote.text = mAddressSelected!!.additionalNote
            binding.tvMobileNumber.text = mAddressSelected!!.mobileNumber

            if (mAddressSelected!!.otherDetails.isNotEmpty()) {
                binding.tvCheckoutOtherDetails.visibility = View.VISIBLE
                binding.tvCheckoutOtherDetails.text = mAddressSelected!!.otherDetails
            }
        }


        getAllProducts()

        binding.btnPlaceOrder.setOnClickListener {
            placeAnOrder()
        }
    }


    fun getAllProducts() {
        showProgressDialog()
        FireStoreClass().getAllProducts(this)
    }

    fun getAllProductsSuccessfully(products: ArrayList<Product>) {
        mProductsList = products
        getAllCartItems()
    }

    fun getAllCartItems() {
        FireStoreClass().getProductsInCart(this)
    }

    fun getAllCartItemsSuccessfully(cartItems: ArrayList<Cart>) {
        hideProgressDialog()
        for (products in mProductsList) {
            for (item in cartItems) {
                if (products.product_id == item.product_id) {
                    item.stock_quantity = products.stock_quantity
                }
            }
        }
        mCartsList = cartItems

        binding.rvCartListItems.layoutManager=LinearLayoutManager(this)
        binding.rvCartListItems.setHasFixedSize(true)

        val itemsAdapter=CartAdapter(this,mCartsList,false)
        binding.rvCartListItems.adapter=itemsAdapter

        for (item in mCartsList) {

            val availableQuantity = item.stock_quantity.toInt()

            if (availableQuantity > 0) {
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                mSubTotal += (price * quantity)
            }
        }

        binding.tvCheckoutSubTotal.text = "$$mSubTotal"

        binding.tvCheckoutShippingCharge.text = "$10.0"

        if (mSubTotal > 0) {
            binding.llCheckoutPlaceOrder.visibility = View.VISIBLE

            mTotalAmount = mSubTotal + 10.0
            binding.tvCheckoutTotalAmount.text = "$$mTotalAmount"
        } else {
            binding.llCheckoutPlaceOrder.visibility = View.GONE
        }
    }

    fun updateAllDetailsSuccessfully(){
        hideProgressDialog()

        Toast.makeText(this, "Your order placed successfully.", Toast.LENGTH_SHORT)
            .show()

        val intent = Intent(this, DashBoardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun orderPlacedSuccess() {
        FireStoreClass().updateAllDetails(this,mCartsList)
    }

    private fun placeAnOrder() {

        showProgressDialog()

        val order = Order(
            FireStoreClass().getCurrentUser(),
            mCartsList,
            mAddressSelected!!,
            "My order ${System.currentTimeMillis()}",
            mCartsList[0].image,
            mSubTotal.toString(),
            "10.0",
            mTotalAmount.toString(),
        )

        FireStoreClass().placeOrder(this, order)

    }


    fun supportActionBar() {
        setSupportActionBar(binding.toolbarCheckoutActivity)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic__back)
        }

        binding.toolbarCheckoutActivity.setNavigationOnClickListener { onBackPressed() }
    }
}