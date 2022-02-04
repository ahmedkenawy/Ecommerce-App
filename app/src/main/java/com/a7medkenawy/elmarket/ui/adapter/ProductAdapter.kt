package com.a7medkenawy.elmarket.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ProductCustomViewBinding
import com.a7medkenawy.elmarket.databinding.ProductDashboardCustomViewBinding
import com.a7medkenawy.elmarket.models.Product
import com.bumptech.glide.Glide

class ProductAdapter(val context: Context, val list: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductCustomViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding)


    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productItem = list[position]
        Glide.with(context).load(productItem.image).into(holder.binding.productIV)
        holder.binding.productTitle.text = productItem.title
        holder.binding.productPrice.text = "${productItem.price}$"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ProductViewHolder(val binding: ProductCustomViewBinding) :
        RecyclerView.ViewHolder(binding.root)

}
