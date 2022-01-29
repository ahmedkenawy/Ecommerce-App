package com.a7medkenawy.elmarket.utils

import android.app.Activity
import android.content.SharedPreferences
import android.net.Uri
import android.webkit.MimeTypeMap

object Constants {
    const val USERS = "users"
    const val SharedPreferencesName = "UserSharedPreferences"
    const val userName = "username"
    const val USER_DETAILS = "userDetails"
    const val USER_Profile_Image = "userProfileImage"
    const val SP_Profile_Image = "userProfileImage"


    fun getImageExtension(activity: Activity, ImageUri: Uri): String {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(ImageUri))!!
    }
}