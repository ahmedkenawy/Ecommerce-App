package com.a7medkenawy.elmarket.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.ActivityAddAddressBinding
import com.a7medkenawy.elmarket.firestore.FireStoreClass
import com.a7medkenawy.elmarket.models.Address
import com.a7medkenawy.elmarket.ui.adapter.AddressAdapter
import com.a7medkenawy.elmarket.utils.Constants
import com.a7medkenawy.elmarket.utils.swipe.SwipeToDeleteCallback
import com.a7medkenawy.elmarket.utils.swipe.SwipeToEditCallback

class AddressActivity : BaseActivity(), View.OnClickListener {

    private var mAddress: Boolean = false

    private lateinit var binding: ActivityAddAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        binding.addAddressButton.setOnClickListener(this)

        if (intent.hasExtra(Constants.SELECT_ADDRESS)) {
            mAddress = intent.getBooleanExtra(Constants.SELECT_ADDRESS, false)
        }

        if (mAddress) {
            binding.toolbarTv.text = resources.getString(R.string.select_address)
        }

        FireStoreClass().getAllAddressesFromDatabase(this)

    }

    fun displayAddressesInRecyclerView(addressesList: ArrayList<Address>) {
        if (addressesList.size > 0) {
            binding.addAddressRv.visibility = View.VISIBLE
            binding.addAddressNoAddressFoundLottie.visibility = View.GONE
            binding.addAddressNoAddressFoundTv.visibility = View.GONE

            val addressesAdapter = AddressAdapter(this, addressesList,mAddress  )
            binding.addAddressRv.layoutManager = LinearLayoutManager(this)
            binding.addAddressRv.adapter = addressesAdapter

            if (!mAddress) {
                swipeToEdit()
                swipeToDelete(addressesList)
            }

        } else {
            binding.addAddressRv.visibility = View.GONE
            binding.addAddressNoAddressFoundLottie.visibility = View.VISIBLE
            binding.addAddressNoAddressFoundTv.visibility = View.VISIBLE
        }
    }

    private fun swipeToEdit() {
        val swipeEdit = object : SwipeToEditCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipeAdapter = binding.addAddressRv.adapter as AddressAdapter
                swipeAdapter.notifyEditItem(this@AddressActivity, viewHolder.adapterPosition)
            }

        }

        val swipeEditTouchHelper = ItemTouchHelper(swipeEdit)
        swipeEditTouchHelper.attachToRecyclerView(binding.addAddressRv)
    }

    private fun swipeToDelete(addressesList: ArrayList<Address>) {
        val swipeDelete = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showProgressDialog()
                FireStoreClass().deleteAddress(
                    this@AddressActivity,
                    addressesList[viewHolder.adapterPosition].id
                )
            }

        }

        val swipeDeleteTouchHelper = ItemTouchHelper(swipeDelete)
        swipeDeleteTouchHelper.attachToRecyclerView(binding.addAddressRv)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK){
            FireStoreClass().getAllAddressesFromDatabase(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_address_button -> {
                val intent = Intent(this, EditAddressActivity::class.java)
                startActivityForResult(intent,Constants.SELECT_ADDRESS_REQUEST_CODE)
            }
        }
    }
}