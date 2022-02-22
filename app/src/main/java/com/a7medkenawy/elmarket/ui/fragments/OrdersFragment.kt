package com.a7medkenawy.elmarket.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.elmarket.databinding.FragmentOrdersBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Order
import com.a7medkenawy.elmarket.ui.adapter.MyOrdersListAdapter

class OrdersFragment : BaseFragment() {

    lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onResume() {
        super.onResume()

        getMyOrdersList()
    }

    private fun getMyOrdersList() {
        showProgressDialog()

        FireStoreClass().getMyOrdersList(this@OrdersFragment)
    }

    fun populateOrdersListInUI(ordersList: ArrayList<Order>) {

        hideProgressDialog()

        if (ordersList.size > 0) {

            binding.rvMyOrderItems.visibility = View.VISIBLE
            binding.tvNoOrdersFound.visibility = View.GONE

           binding.rvMyOrderItems.layoutManager = LinearLayoutManager(activity)
            binding.rvMyOrderItems.setHasFixedSize(true)

            val myOrdersAdapter = MyOrdersListAdapter(requireActivity(), ordersList)
            binding.rvMyOrderItems.adapter = myOrdersAdapter
        } else {
            binding.rvMyOrderItems.visibility = View.GONE
            binding.tvNoOrdersFound.visibility = View.VISIBLE
        }
    }
}