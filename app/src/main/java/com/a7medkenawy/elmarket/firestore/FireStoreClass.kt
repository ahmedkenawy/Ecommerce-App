package com.a7medkenawy.elmarket.firestore

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.a7medkenawy.elmarket.activities.LoginActivity
import com.a7medkenawy.elmarket.activities.RegisterActivity
import com.a7medkenawy.elmarket.models.User
import com.a7medkenawy.elmarket.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    fun getCurrentUser(): String {
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
                val sharedPreferences = activity.getSharedPreferences(
                    Constants.SharedPreferencesName,
                    Context.MODE_PRIVATE
                )
                sharedPreferences.edit()
                    .putString(Constants.userName, "${user?.firstName} ${user?.lastName}").apply()
            }
            .addOnFailureListener {
                Toast.makeText(activity.baseContext, it.message, Toast.LENGTH_LONG).show()
            }
    }
}