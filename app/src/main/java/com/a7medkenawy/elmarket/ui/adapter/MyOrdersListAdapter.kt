package com.a7medkenawy.elmarket.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.elmarket.databinding.ProductCustomViewBinding
import com.a7medkenawy.elmarket.models.Order
import com.bumptech.glide.Glide

open class MyOrdersListAdapter(
    private val context: Context,
    private var list: ArrayList<Order>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            ProductCustomViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            Glide.with(context).load(model.image).into(holder.binding.productIV)

            holder.binding.productTitle.text = model.title
            holder.binding.productPrice.text = "$${model.total_amount}"

            holder.binding.productDelete.visibility = View.GONE
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }


    class MyViewHolder(var binding: ProductCustomViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}
