package com.a7medkenawy.elmarket.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityAddProductBinding.inflate
import com.a7medkenawy.elmarket.databinding.ActivityCartListBinding.inflate
import com.a7medkenawy.elmarket.databinding.ItemCartLayoutBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Cart
import com.a7medkenawy.elmarket.ui.activities.CartListActivity
import com.a7medkenawy.elmarket.utils.Constants
import com.bumptech.glide.Glide

class CartAdapter(val context: Activity, val list: ArrayList<Cart>, private val updateCartItem: Boolean) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = list[position]
        with(holder.binding) {
            Glide.with(context).load(cartItem.image).into(ivCartItemImage)
            tvCartItemTitle.text = cartItem.title
            tvCartItemPrice.text = "${cartItem.price}$"
            tvCartQuantity.text = cartItem.cart_quantity



            if (cartItem.cart_quantity == "0") {
                ibAddCartItem.visibility = View.GONE
                ibRemoveCartItem.visibility = View.GONE

                tvCartQuantity.text = context.resources.getString(R.string.out_of_stock)
                tvCartQuantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSnackBarError
                    )
                )

            } else {
                if(!updateCartItem){
                    ibDeleteCartItem.visibility=View.GONE
                    ibRemoveCartItem.visibility=View.GONE
                    ibAddCartItem.visibility=View.GONE
                }else{
                    ibAddCartItem.visibility = View.VISIBLE
                    ibRemoveCartItem.visibility = View.VISIBLE
                }

                tvCartQuantity.text = cartItem.cart_quantity
                tvCartQuantity.setTextColor(ContextCompat.getColor(context, R.color.black))
            }


            ibDeleteCartItem.setOnClickListener {
                when (context) {
                    is CartListActivity -> {
                        context.setAlertDialog(cartItem.id)
                    }

                }
            }


            var stockQuantity = cartItem.stock_quantity.toInt()
            var cartQuantity = cartItem.cart_quantity.toInt()
            var quantityHashtable = HashMap<String, Any>()
            ibAddCartItem.setOnClickListener {
                cartQuantity++
                if (cartQuantity >= stockQuantity) {
                    tvCartQuantity.text = stockQuantity.toString()
                    quantityHashtable[Constants.CART_Quantity] = tvCartQuantity.text.toString()
                    FireStoreClass().updateCartQuantity(context as CartListActivity, cartItem.id, quantityHashtable)
                } else {
                    tvCartQuantity.text = cartQuantity.toString()
                    quantityHashtable[Constants.CART_Quantity] = tvCartQuantity.text.toString()
                    FireStoreClass().updateCartQuantity(context as CartListActivity, cartItem.id, quantityHashtable)
                }
            }

            ibRemoveCartItem.setOnClickListener {
                cartQuantity--
                if (cartQuantity <= 0) {
                    tvCartQuantity.text = "1"
                    quantityHashtable[Constants.CART_Quantity] = tvCartQuantity.text.toString()
                    FireStoreClass().updateCartQuantity(context as CartListActivity, cartItem.id, quantityHashtable)
                } else {
                    tvCartQuantity.text = cartQuantity.toString()
                    quantityHashtable[Constants.CART_Quantity] = tvCartQuantity.text.toString()
                    FireStoreClass().updateCartQuantity(context as CartListActivity, cartItem.id, quantityHashtable)
                }
            }
        }

    }

    override fun getItemCount() = list.size

    fun updatePrice(quantity: Int, price: Double): String {
        var newPrice = quantity * price
        return newPrice.toString()
    }

    class CartViewHolder(val binding: ItemCartLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}