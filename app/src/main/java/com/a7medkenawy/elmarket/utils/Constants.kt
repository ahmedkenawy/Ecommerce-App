package com.a7medkenawy.elmarket.utils

import android.app.Activity
import android.content.SharedPreferences
import android.net.Uri
import android.webkit.MimeTypeMap

object Constants {
    //  Collections in Cloud FireStore
    const val USERS = "users"
    const val PRODUCT = "products"
    const val CART_ITEMS = "cart_items"
    const val Addresses = "addresses"


    const val SharedPreferencesName = "UserSharedPreferences"
    const val userName = "username"
    const val USER_DETAILS = "userDetails"
    const val USER_Profile_Image = "userProfileImage"
    const val SP_Profile_Image = "userProfileImage"
    const val TAG = "tag"


    const val USER_ID = "user_id"
    const val PRODUCT_ID = "product_id"

    const val PROFILE_IMAGE = "user profile image"
    const val PRODUCT_IMAGE = "product image"


    const val CART_QUANTITY: String = "1"
    const val CART_Quantity: String = "cart_quantity"
    const val CART_Price: String = "price"
    const val CART_Base_Price: String = "base_price"

    const val AddressDetails: String = "addressDetails"
    const val SELECT_ADDRESS: String = "select_address"
    const val SELECT_ADDRESS_REQUEST_CODE: Int = 55


    fun getImageExtension(activity: Activity, ImageUri: Uri): String {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(ImageUri))!!
    }
}