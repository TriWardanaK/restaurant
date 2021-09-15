package com.example.restaurant.api

import com.example.restaurant.response.DataMenuResponse
import com.example.restaurant.response.FilterDataMenuResponse
import com.example.restaurant.response.SearchDataMenuResponse
import com.example.restaurant.response.TypeDataMenuResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    //menu

    @GET("/menu")
    fun getMenu(): Call<DataMenuResponse>

    @GET("search/menu")
    fun getSearchMenu(@Query("query")text: String): Call<SearchDataMenuResponse>

    @GET("filter/menu")
    fun getFilterMenu(@Query("nama_type")text: String?,
                      @Query("harga_min")text2: String?,
                      @Query("harga_max")text3: String?)
    : Call<FilterDataMenuResponse>

    @GET("/dialogfilter/type_menu")
    fun getTypeMenu(): Call<TypeDataMenuResponse>

}