package com.example.tugasbesarpsi.`class`

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val email: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val type: String = "User"
): Parcelable