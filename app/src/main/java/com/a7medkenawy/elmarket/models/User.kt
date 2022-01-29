package com.a7medkenawy.elmarket.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String? = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val mobile: Long = 0,
    val image:String="",
    val gender: String = "",
    val completed: Int = 0
):Parcelable