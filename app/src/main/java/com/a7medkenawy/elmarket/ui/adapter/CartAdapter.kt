package com.a7medkenawy.elmarket.ui.adapter

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
import com.a7medkenawy.elmarket.models.Cart
import com.bumptech.glide.Glide

class CartAdapter(val context: Context, val list: ArrayList<Cart>) :
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
                tvCartQuantity.setTextColor(ContextCompat.getColor(context,R.color.colorSnackBarError))

            } else {
                ibAddCartItem.visibility = View.VISIBLE
                ibRemoveCartItem.visibility = View.VISIBLE
                tvCartQuantity.text = cartItem.cart_quantity
                tvCartQuantity.setTextColor(ContextCompat.getColor(context,R.color.black))
            }



        }

    }

    override fun getItemCount() = list.size


    class CartViewHolder(val binding: ItemCartLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}