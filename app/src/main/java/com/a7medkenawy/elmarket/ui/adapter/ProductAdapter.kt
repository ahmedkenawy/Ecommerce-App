package com.a7medkenawy.elmarket.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ProductCustomViewBinding
import com.a7medkenawy.elmarket.databinding.ProductDashboardCustomViewBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.ui.activities.ProductDetailsActivity
import com.a7medkenawy.elmarket.ui.fragments.ProductsFragment
import com.a7medkenawy.elmarket.utils.Constants
import com.bumptech.glide.Glide

class ProductAdapter(
    val context: Context,
    val list: ArrayList<Product>,
    val fragment: ProductsFragment
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductCustomViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding)


    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productItem = list[position]

        with(holder.binding) {
            Glide.with(context).load(productItem.image).into(productIV)
            productTitle.text = productItem.title
            productPrice.text = "${productItem.price}$"


            productDelete.setOnClickListener {
                fragment.setAlertDialog(productItem.product_id)
            }

            holder.binding.root.setOnClickListener {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra(Constants.PRODUCT_ID, productItem.product_id)
                intent.putExtra(Constants.USER_ID, productItem.user_id)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ProductViewHolder(val binding: ProductCustomViewBinding) :
        RecyclerView.ViewHolder(binding.root)

}
