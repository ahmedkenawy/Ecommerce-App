package com.a7medkenawy.elmarket.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityAddAddressBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Address
import com.a7medkenawy.elmarket.ui.adapter.AddressAdapter

class AddressActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.addAddressButton.setOnClickListener(this)

        FireStoreClass().getAllAddressesFromDatabase(this)

    }

    fun displayAddressesInRecyclerView(addressesList: ArrayList<Address>) {
        if (addressesList.size > 0) {
            binding.addAddressRv.visibility = View.VISIBLE
            binding.addAddressNoAddressFoundLottie.visibility = View.GONE
            binding.addAddressNoAddressFoundTv.visibility = View.GONE

            val addressesAdapter = AddressAdapter(this, addressesList)
            binding.addAddressRv.layoutManager = LinearLayoutManager(this)
            binding.addAddressRv.adapter = addressesAdapter

        } else {
            binding.addAddressRv.visibility = View.GONE
            binding.addAddressNoAddressFoundLottie.visibility = View.VISIBLE
            binding.addAddressNoAddressFoundTv.visibility = View.VISIBLE
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.addAddressToolbar)

        val actionBar = supportActionBar
        actionBar?.title = ""
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic__back)
        }

        binding.addAddressToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_address_button -> {
                val intent = Intent(this, EditAddressActivity::class.java)
                startActivity(intent)
            }
        }
    }
}