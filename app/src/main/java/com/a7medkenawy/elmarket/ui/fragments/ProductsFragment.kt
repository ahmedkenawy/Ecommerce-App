package com.a7medkenawy.elmarket.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.FragmentHomeBinding
import com.a7medkenawy.elmarket.databinding.FragmentProductsBinding
import com.a7medkenawy.elmarket.ui.activities.AddProductActivity

class ProductsFragment : Fragment() {

    lateinit var binding: FragmentProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_product -> {
                startActivity(Intent(requireContext(), AddProductActivity::class.java))
            }
        }
        return true
    }

}