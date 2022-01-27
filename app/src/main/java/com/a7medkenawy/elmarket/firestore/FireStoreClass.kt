package com.a7medkenawy.elmarket.firestore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.a7medkenawy.elmarket.activities.LoginActivity
import com.a7medkenawy.elmarket.activities.MainActivity
import com.a7medkenawy.elmarket.activities.RegisterActivity
import com.a7medkenawy.elmarket.activities.UserProfileActivity
import com.a7medkenawy.elmarket.models.User
import com.a7medkenawy.elmarket.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreClass {
    private val fireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, user: User) {

        fireStore.collection(Constants.USERS)
            .document(user.id!!)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(activity.baseContext, "Successfully", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(activity.baseContext, "Successfully", Toast.LENGTH_LONG).show()
            }
    }

    private fun getCurrentUser(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun getUserDetails(activity: LoginActivity) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUser())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)

                when (user!!.completed) {
                    0 -> {
                        sendDataToUserProfile(activity, user)
                    }
                    1 -> {
                        sendDataToMainActivity(activity, user)
                    }
                }


            }
            .addOnFailureListener {
                Toast.makeText(activity.baseContext, it.message, Toast.LENGTH_LONG).show()
            }
    }

    private fun sendDataToUserProfile(activity: LoginActivity, user: User) {
        val intent = Intent(activity.baseContext, UserProfileActivity::class.java)
        intent.putExtra(Constants.USER_DETAILS, user)
        activity.startActivity(intent)
    }


    private fun sendDataToMainActivity(activity: LoginActivity, user: User) {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.SharedPreferencesName,
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit()
            .putString(Constants.userName, "${user?.firstName} ${user?.lastName}").apply()

        val intent = Intent(activity.baseContext, MainActivity::class.java)
        activity.startActivity(intent)
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
                                MainActivity::class.java
                            )
                        )
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(activity.baseContext, "Failed", Toast.LENGTH_LONG).show()
                activity.finish()
            }
    }


}