package com.a7medkenawy.elmarket.ui.fragments

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.FragmentProductsBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.ui.activities.AddProductActivity
import com.a7medkenawy.elmarket.ui.adapter.ProductAdapter

class ProductsFragment : Fragment() {

    lateinit var binding: FragmentProductsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FireStoreClass().getProductDetails(this)
    }

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

    private fun inVisibleLottieFile() {
        binding.emptyCart.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                binding.emptyCart.visibility = View.GONE;
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
    }


    fun getProductsDetails(products: ArrayList<Product>) {
        if (products.size > 0) {
            binding.emptyCart.cancelAnimation()
            binding.emptyCart.visibility = View.GONE
            binding.emptyTv.visibility = View.GONE
            binding.productRV.visibility = View.VISIBLE
            binding.productRV.layoutManager = LinearLayoutManager(activity)
            binding.productRV.setHasFixedSize(true)
            val RVAdapter = ProductAdapter(requireActivity(), products, this)
            binding.productRV.adapter = RVAdapter
        } else {
            Toast.makeText(requireContext(), "A7A", Toast.LENGTH_LONG).show()
        }
    }

    fun setAlertDialog(productID:String) {
        val builder = AlertDialog.Builder(
            requireContext()
        )
        builder.setIcon(R.drawable.ic_vector_warning)
        builder.setTitle("Delete The Product")
        builder.setMessage("Are You sure you want to delete the product? ")
        builder.setCancelable(true)

        builder.setPositiveButton(
            "Yes"
        ) { dialog, id ->
            FireStoreClass().deleteProduct(this,productID)
            FireStoreClass().getProductDetails(this)
        }

        builder.setNegativeButton(
            "No"
        ) { dialog, id -> dialog.cancel() }

        val alert = builder.create()
        alert.show()
    }

}

