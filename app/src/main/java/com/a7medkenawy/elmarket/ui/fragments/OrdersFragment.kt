package com.a7medkenawy.elmarket.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.a7medkenawy.elmarket.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

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

}