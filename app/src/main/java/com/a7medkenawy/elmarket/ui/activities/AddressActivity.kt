package com.a7medkenawy.elmarket.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityAddAddressBinding

class AddressActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.addAddressButton.setOnClickListener(this)

    }

    private fun setupActionBar() {

        setSupportActionBar(binding.addAddressToolbar)

        val actionBar = supportActionBar
        actionBar?.title=""
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