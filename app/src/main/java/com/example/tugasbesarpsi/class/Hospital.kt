package com.example.tugasbesarpsi.`class`

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hospital(
    val idHospital: String = "",
    val avatar: String = "",
    val name: String = "",
    val address: String = "",
    var doctor: List<Doctor> = emptyList()
): Parcelable