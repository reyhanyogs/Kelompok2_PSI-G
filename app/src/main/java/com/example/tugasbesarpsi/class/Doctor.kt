package com.example.tugasbesarpsi.`class`

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doctor(
    val avatar: String = "",
    val name: String = "",
    val age: String = "",
    val gender: String = "",
    val specialist: String = "",
    var patient:  List<Person> = emptyList()
): Parcelable