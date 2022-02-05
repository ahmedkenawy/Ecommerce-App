package com.a7medkenawy.elmarket.firestore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.a7medkenawy.elmarket.models.Product
import com.a7medkenawy.elmarket.models.User
import com.a7medkenawy.elmarket.ui.activities.*
import com.a7medkenawy.elmarket.ui.fragments.HomeFragment
import com.a7medkenawy.elmarket.ui.fragments.ProductsFragment
import com.a7medkenawy.elmarket.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

}