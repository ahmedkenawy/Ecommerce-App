package com.a7medkenawy.elmarket.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityCartListBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Cart
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.ui.adapter.CartAdapter
import com.a7medkenawy.elmarket.utils.Constants

class CartListActivity : BaseActivity() {
    private var mProductsList= ArrayList<Product>()
    private lateinit var mCartListItems: ArrayList<Cart>

    lateinit var binding: ActivityCartListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBar()

        binding.btnCheckout.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            intent.putExtra(Constants.SELECT_ADDRESS, true)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        getProductsFromDatabase()
    }


    fun showCartList(carts: ArrayList<Cart>) {

        for (product in mProductsList) {
            for (cart in carts) {
                if (product.product_id == cart.product_id) {

                    cart.stock_quantity = product.stock_quantity

                    if (product.stock_quantity.toInt() == 0) {
                        cart.cart_quantity = product.stock_quantity
                    }
                }
            }
        }

        mCartListItems = carts


        if (mCartListItems.size > 0) {
            binding.llCheckout.visibility = View.VISIBLE
            binding.rvCartItemsList.visibility = View.VISIBLE
            binding.tvNoCartItemFound.visibility = View.GONE

            val cartAdapter = CartAdapter(this, mCartListItems,true)
            binding.rvCartItemsList.layoutManager = LinearLayoutManager(this)
            binding.rvCartItemsList.adapter = cartAdapter

            var subTotal: Double = 0.0
            for (item in mCartListItems) {

                val availableQuantity = item.stock_quantity.toInt()

                if (availableQuantity > 0) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()

                    subTotal += (price * quantity)
                }
            }
            binding.tvSubTotal.text = subTotal.toString()
            val shipping = "10"
            binding.tvShippingCharge.text = "$shipping$"

            val total = subTotal + shipping.toDouble()
            binding.tvTotalAmount.text = total.toString()


        } else {
            binding.llCheckout.visibility = View.GONE
            binding.rvCartItemsList.visibility = View.GONE
            binding.tvNoCartItemFound.visibility = View.VISIBLE
        }
    }

    private fun getProductsFromDatabase() {
        FireStoreClass().getAllProducts(this)
        getProductsInCartsFromDatabase()
    }

    fun getAllProduct(products: ArrayList<Product>) {
        mProductsList = products
    }

    fun getProductsInCartsFromDatabase() {
        FireStoreClass().getProductsInCart(this)
    }

    fun setToolBar() {
        setSupportActionBar(binding.toolbarCartListActivity)
        val actionBar = supportActionBar
        actionBar?.title = ""
        actionBar?.let {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic__back)
            binding.toolbarCartListActivity.setNavigationOnClickListener { onBackPressed() }
        }
    }

    fun setAlertDialog(productID: String) {
        val builder = AlertDialog.Builder(
            this
        )
        builder.setIcon(R.drawable.ic_vector_warning)
        builder.setTitle("Delete The Product")
        builder.setMessage("Are You sure you want to delete the product? ")
        builder.setCancelable(true)

        builder.setPositiveButton(
            "Yes"
        ) { dialog, id ->
            FireStoreClass().deleteItemFromCart(baseContext, productID)
            getProductsInCartsFromDatabase()
        }

        builder.setNegativeButton(
            "No"
        ) { dialog, id -> dialog.cancel() }

        val alert = builder.create()
        alert.show()
    }
}