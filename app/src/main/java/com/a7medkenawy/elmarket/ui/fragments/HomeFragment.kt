package com.a7medkenawy.elmarket.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.FragmentHomeBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.ui.activities.CartListActivity
import com.a7medkenawy.elmarket.ui.activities.SettingsActivity
import com.a7medkenawy.elmarket.ui.adapter.DashboardProductAdapter
import com.a7medkenawy.elmarket.ui.adapter.ProductAdapter
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val imageList = java.util.ArrayList<SlideModel>()

        imageList.add(
            SlideModel(
                R.drawable.one,
                "The animal population decreased by 58 percent in 42 years.",
                ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.two,
                "Elephants and tigers may become extinct.",
                ScaleTypes.FIT
            )
        )
        imageList.add(SlideModel(R.drawable.three, "And people do that.", ScaleTypes.FIT))
        imageList.add(
            SlideModel(
                R.drawable.four,
                "The animal population decreased by 58 percent in 42 years.",
                ScaleTypes.FIT
            )
        )


        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.startSliding(3000)

        FireStoreClass().getDashBoardProductsDetails(this)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings -> {
                requireActivity().startActivity(
                    Intent(
                        requireContext(),
                        SettingsActivity::class.java
                    )
                )

            }
            R.id.go_to_cart -> {
                val intent = Intent(requireContext(), CartListActivity::class.java)
                startActivity(intent)
            }
        }

        return false
    }

    fun getDashBoardProductsDetails(products: ArrayList<Product>) {
        if (products.size > 0) {
            binding.DashBoardRV.layoutManager = GridLayoutManager(activity, 2)
            binding.DashBoardRV.setHasFixedSize(true)
            val RVAdapter = DashboardProductAdapter(requireActivity(), products)
            binding.DashBoardRV.adapter = RVAdapter
        } else {
            Toast.makeText(requireContext(), "A7A", Toast.LENGTH_LONG).show()
        }
    }
}