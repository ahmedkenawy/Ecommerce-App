package com.a7medkenawy.elmarket.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.elmarket.databinding.ProductCustomViewBinding
import com.a7medkenawy.elmarket.databinding.ProductDashboardCustomViewBinding
import com.a7medkenawy.elmarket.models.Product
import com.bumptech.glide.Glide

class DashboardProductAdapter(val context: Context, val list: ArrayList<Product>) :
    RecyclerView.Adapter<DashboardProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductDashboardCustomViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ProductViewHolder(binding)


    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productItem = list[position]
        Glide.with(context).load(productItem.image).into(holder.binding.dashboardProductIv)
        holder.binding.dashboardProductTitle.text = productItem.title
        holder.binding.dashboardProductDescription.text = productItem.description
        holder.binding.dashboardProductPrice.text = "${productItem.price}$"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ProductViewHolder(val binding: ProductDashboardCustomViewBinding) :
        RecyclerView.ViewHolder(binding.root)

}
