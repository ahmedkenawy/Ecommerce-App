package com.a7medkenawy.elmarket.firestore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.a7medkenawy.elmarket.models.Address
import com.a7medkenawy.elmarket.models.Cart
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.models.User
import com.a7medkenawy.elmarket.ui.activities.*
import com.a7medkenawy.elmarket.ui.fragments.HomeFragment
import com.a7medkenawy.elmarket.ui.fragments.ProductsFragment
import com.a7medkenawy.elmarket.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage

class FireStoreClass {

    private val fireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, user: User) {

        fireStore.collection(Constants.USERS)
            .document(user.id!!)
            .set(user)
            .addOnSuccessListener {
                activity.showCustomToast()
                saveUserName(activity, user.firstName, user.lastName)
                activity.startActivity(Intent(activity.baseContext, LoginActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(activity.baseContext, "Successfully", Toast.LENGTH_LONG).show()
            }
    }

    private fun saveUserName(activity: RegisterActivity, firstName: String, lastName: String) {
        val sharedPreferences =
            activity.getSharedPreferences(Constants.SharedPreferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
            .putString(Constants.userName, "$firstName $lastName ")
        editor.apply()
    }


    fun getCurrentUser(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun getUserDetails(activity: Activity) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUser())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                when (activity) {

                    is LoginActivity -> {
                        when (user!!.completed) {
                            0 -> {
                                sendDataToUserProfile(activity, user!!)
                            }
                            1 -> {
                                sendDataToMainActivity(activity, user!!)
                            }
                        }
                    }

                    is SettingsActivity -> {
                        if (user != null) {
                            activity.insertDataInViews(user)
                        }
                    }
                }


            }
            .addOnFailureListener {
                Toast.makeText(activity.baseContext, it.message, Toast.LENGTH_LONG).show()

            }
    }

    private fun sendDataToUserProfile(activity: Activity, user: User) {
        val intent = Intent(activity.baseContext, UserProfileActivity::class.java)
        intent.putExtra(Constants.USER_DETAILS, user)
        activity.startActivity(intent)
        activity.finish()
    }


    private fun sendDataToMainActivity(activity: Activity, user: User) {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.SharedPreferencesName,
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit()
            .putString(Constants.userName, "${user?.firstName} ${user?.lastName}").apply()

        val intent = Intent(activity.baseContext, DashBoardActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }


    fun updateUserDetails(activity: Activity, userHashMap: HashMap<String, Any>) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUser())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity) {
                    is UserProfileActivity -> {
                        activity.startActivity(
                            Intent(
                                activity.baseContext,
                                DashBoardActivity::class.java
                            )
                        )
                        activity.finish()
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(activity.baseContext, "Failed", Toast.LENGTH_LONG).show()
                activity.finish()
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageUri: Uri, imageType: String) {

        val fRef = FirebaseStorage.getInstance().reference.child(
            "${imageType} ${System.currentTimeMillis()}.${
                Constants.getImageExtension(
                    activity,
                    imageUri
                )
            }"
        )

        fRef.putFile(imageUri).addOnSuccessListener { taskSnapsShot ->

            taskSnapsShot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener {
                    when (activity) {
                        is UserProfileActivity -> {
                            activity.uploadProfileImage(it.toString())
                        }
                        is AddProductActivity -> {
                            activity.uploadProductImage(it.toString())
                            activity.hideProgressDialog()
                        }
                    }
                }
        }.addOnFailureListener {
            Toast.makeText(
                activity.baseContext,
                it.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    fun uploadProductDetails(activity: AddProductActivity, product: Product) {
        fireStore.collection(Constants.PRODUCT)
            .document()
            .set(product)
            .addOnSuccessListener {
                activity.startActivity(Intent(activity.baseContext, DashBoardActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(activity.baseContext, it.toString(), Toast.LENGTH_LONG).show()
            }
    }

    fun getProductDetails(fragment: Fragment) {
        fireStore.collection(Constants.PRODUCT)
            .whereEqualTo(Constants.USER_ID, getCurrentUser())
            .get()
            .addOnSuccessListener { snapShot ->
                val productList: ArrayList<Product> = ArrayList()
                for (i in snapShot.documents) {
                    val product = i.toObject(Product::class.java)
                    product?.product_id = i.id
                    productList.add(product!!)
                }

                when (fragment) {
                    is ProductsFragment -> {
                        fragment.getProductsDetails(productList)
                    }
                }
            }
            .addOnFailureListener {


            }
    }

    fun getDashBoardProductsDetails(fragment: HomeFragment) {
        fireStore.collection(Constants.PRODUCT)
            .get()
            .addOnSuccessListener { snapShot ->
                val productList: ArrayList<Product> = ArrayList()
                for (i in snapShot.documents) {
                    val product = i.toObject(Product::class.java)
                    product?.product_id = i.id
                    productList.add(product!!)
                }
                fragment.getDashBoardProductsDetails(productList)

            }
            .addOnFailureListener {

            }
    }

    fun deleteProduct(fragment: Fragment, productId: String) {
        fireStore.collection(Constants.PRODUCT)
            .document(productId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(fragment.requireContext(), "Done", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(fragment.requireContext(), "Error", Toast.LENGTH_LONG).show()
            }

    }

    fun getProductByID(activity: Activity, productId: String) {
        fireStore.collection(Constants.PRODUCT)
            .document(productId)
            .get()
            .addOnSuccessListener { document ->
                val product = document.toObject(Product::class.java)
                when (activity) {
                    is ProductDetailsActivity -> {
                        product?.let {
                            activity.setProductDetails(product)
                        }
                    }
                }
            }
            .addOnFailureListener {

            }
    }

    fun addToCarts(activity: ProductDetailsActivity, cart: Cart) {
        fireStore.collection(Constants.CART_ITEMS)
            .document()
            .set(cart)
            .addOnSuccessListener {
                activity.cartAddedSuccessfully()
            }
            .addOnFailureListener { }
    }

    fun checkIfProductExist(activity: ProductDetailsActivity, productId: String) {
        fireStore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.PRODUCT_ID, productId)
            .whereEqualTo(Constants.USER_ID, getCurrentUser())
            .get()
            .addOnSuccessListener {
                if (it.documents.size > 0) {
                    activity.productExist()
                }
            }
            .addOnFailureListener { }
    }

    fun getProductsInCart(activity: Activity) {
        fireStore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUser())
            .get()
            .addOnSuccessListener {
                val cartItems = ArrayList<Cart>()
                for (i in it.documents) {
                    val product = i.toObject(Cart::class.java)
                    product?.id = i.id
                    cartItems.add(product!!)
                }

                when (activity) {
                    is CartListActivity -> {
                        activity.showCartList(cartItems)
                    }
                }

            }
            .addOnFailureListener { }
    }


    fun getAllProducts(activity: CartListActivity) {
        fireStore.collection(Constants.PRODUCT)
            .get()
            .addOnSuccessListener { document ->
                val products: ArrayList<Product> = ArrayList()
                for (p in document.documents) {
                    val product = p.toObject(Product::class.java)
                    product!!.product_id = p.id
                    products.add(product!!)
                }
                activity.getAllProduct(products)

            }
            .addOnFailureListener {

            }
    }


    fun deleteItemFromCart(context: Context, cartId: String) {
        fireStore.collection(Constants.CART_ITEMS)
            .document(cartId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "product deleted successfully", Toast.LENGTH_LONG).show()

            }
            .addOnFailureListener {
                Toast.makeText(context, "failed in deleting", Toast.LENGTH_LONG).show()
            }
    }


    fun updateCartQuantity(
        activity: CartListActivity,
        cartId: String,
        cartHashMap: HashMap<String, Any>
    ) {
        fireStore.collection(Constants.CART_ITEMS)
            .document(cartId)
            .update(cartHashMap)
            .addOnSuccessListener {
                activity.getProductsInCartsFromDatabase()
            }
            .addOnFailureListener { }
    }

    fun addAddressToDatabase(activity: EditAddressActivity, address: Address) {
        fireStore.collection(Constants.Addresses)
            .document()
            .set(address, SetOptions.merge())
            .addOnSuccessListener {
                activity.hideProgressDialog()
                activity.backToAddressActivity()
            }
            .addOnFailureListener {
                activity.hideProgressDialog()
            }
    }

    fun getAllAddressesFromDatabase(activity: AddressActivity) {
        fireStore.collection(Constants.Addresses)
            .whereEqualTo(Constants.USER_ID, FireStoreClass().getCurrentUser())
            .get()
            .addOnSuccessListener {
                val addressesList: ArrayList<Address> = ArrayList()
                for (address in it.documents) {
                    val address1 = address.toObject(Address::class.java)
                    address1?.id = address.id
                    addressesList.add(address1!!)
                }
                activity.displayAddressesInRecyclerView(addressesList)

            }

            .addOnFailureListener {

            }
    }


    fun updateAddress(activity: EditAddressActivity, address: Address, addressId: String) {
        fireStore.collection(Constants.Addresses)
            .document(addressId)
            .set(address, SetOptions.merge())
            .addOnSuccessListener {
                activity.hideProgressDialog()
                activity.backToAddressActivity()

            }
            .addOnFailureListener {
                activity.hideProgressDialog()
            }

    }

    fun deleteAddress(activity: AddressActivity, addressId: String) {
        fireStore.collection(Constants.Addresses)
            .document(addressId)
            .delete()
            .addOnSuccessListener {
                activity.hideProgressDialog()
                getAllAddressesFromDatabase(activity)
            }
            .addOnFailureListener { activity.hideProgressDialog() }
    }


}