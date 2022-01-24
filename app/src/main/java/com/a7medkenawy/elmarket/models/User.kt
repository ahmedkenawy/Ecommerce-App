package com.a7medkenawy.elmarket.models

class User(
    val id: String? = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val mobile: Long = 0,
    val gender: String = "",
    val isCompleted: Int = 0
)