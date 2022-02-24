package com.a7medkenawy.elmarket.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.elmarket.databinding.ProductCustomViewBinding
import com.a7medkenawy.elmarket.models.Order
import com.a7medkenawy.elmarket.ui.activities.MyOrderDetailssActivity
import com.a7medkenawy.elmarket.utils.Constants
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

            with(holder.binding) {
                Glide.with(context).load(model.image).into(holder.binding.productIV)

                productTitle.text = model.title
                productPrice.text = "$${model.total_amount}"
                productDelete.visibility = View.GONE

                root.setOnClickListener {
                    val intent=Intent(context,MyOrderDetailssActivity::class.java)
                    intent.putExtra(Constants.SELECTED_ORDER,model)
                    context.startActivity(intent)
                }
            }


        }
    }


    override fun getItemCount(): Int {
        return list.size
    }


    class MyViewHolder(var binding: ProductCustomViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}
