package com.a7medkenawy.elmarket.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.elmarket.databinding.ActivityCartListBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Cart
import com.a7medkenawy.elmarket.ui.adapter.CartAdapter

class CartListActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getProductsFromDatabase()
    }


    fun showCartList(products: ArrayList<Cart>) {
        if (products.size > 0) {
            binding.llCheckout.visibility = View.VISIBLE
            binding.rvCartItemsList.visibility = View.VISIBLE
            binding.tvNoCartItemFound.visibility = View.GONE

            val cartAdapter = CartAdapter(this, products)
            binding.rvCartItemsList.layoutManager = LinearLayoutManager(this)
            binding.rvCartItemsList.adapter = cartAdapter

            var subTotal = 0.0
            for (p in products) {
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
        FireStoreClass().getProductsInCart(this)
    }
}