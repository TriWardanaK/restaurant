package com.example.restaurant.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TypeDataMenuResponse(
    val data: ArrayList<TypeMenuResponse>?
) : Parcelable {
    @Parcelize
    data class TypeMenuResponse(
        @SerializedName("nama_type")
        val namaType: String?,
    ) :Parcelable
}