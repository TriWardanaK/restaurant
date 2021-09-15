package com.example.restaurant.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchDataMenuResponse(
    val data: ArrayList<SearchMenuResponse>?
) : Parcelable {
    @Parcelize
    data class SearchMenuResponse(
        @SerializedName("id_menu")
        val idMenu: Int?,
        @SerializedName("id_type")
        val idType: Int?,
        @SerializedName("nama_menu")
        val namaMenu: String?,
        @SerializedName("nama_type")
        val namaType: String?,
        @SerializedName("harga_menu")
        val hargaMenu: Int?,
        @SerializedName("deskripsi_menu")
        val deskripsiMenu: String?,
        @SerializedName("photo_menu")
        val photoMenu: String?
    ) :Parcelable
}