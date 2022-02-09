package com.a7medkenawy.elmarket.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.elmarket.databinding.ActivityCartListBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Cart
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.ui.adapter.CartAdapter

class CartListActivity : AppCompatActivity() {
    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartListItems: ArrayList<Cart>

    lateinit var binding: ActivityCartListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        for(i in mCartListItems){
            Log.d("YALLLLLAhoy", i.stock_quantity)
        }

        if (mCartListItems.size > 0) {
            binding.llCheckout.visibility = View.VISIBLE
            binding.rvCartItemsList.visibility = View.VISIBLE
            binding.tvNoCartItemFound.visibility = View.GONE

            val cartAdapter = CartAdapter(this, mCartListItems)
            binding.rvCartItemsList.layoutManager = LinearLayoutManager(this)
            binding.rvCartItemsList.adapter = cartAdapter

            var subTotal = 0.0
            for (p in carts) {
                subTotal += p.price.toDouble()
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

    private fun getProductsInCartsFromDatabase() {
        FireStoreClass().getProductsInCart(this)
    }
}