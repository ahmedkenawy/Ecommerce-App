package com.a7medkenawy.elmarket.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.elmarket.databinding.AddressCustomViewBinding
import com.a7medkenawy.elmarket.models.Address

class AddressAdapter(val context: Context, val addressList: ArrayList<Address>) :
    RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            AddressCustomViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val itemAddress = addressList[position]

        with(holder.binding) {
            viewAddressFullName.text = itemAddress.name
            viewAddressType.text = itemAddress.type
            viewAddressAddress.text = itemAddress.address
            viewAddressPhoneNumber.text = itemAddress.mobileNumber
        }
    }

    override fun getItemCount() = addressList.size


    class AddressViewHolder(val binding: AddressCustomViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}