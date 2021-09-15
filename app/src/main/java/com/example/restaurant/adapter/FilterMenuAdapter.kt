package com.example.restaurant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant.R
import com.example.restaurant.response.FilterDataMenuResponse
import kotlinx.android.synthetic.main.item_filter_menu.view.*

class FilterMenuAdapter(private val listMenu: ArrayList<FilterDataMenuResponse.FilterMenuResponse>)
    : RecyclerView.Adapter<FilterMenuAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMenu[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(FilterMenuResponse: FilterDataMenuResponse.FilterMenuResponse){
            with(itemView){
                Glide.with(itemView.context)
                    .load(FilterMenuResponse.photoMenu)
                    .into(photo_menu)

                tv_nama_menu.text = FilterMenuResponse.namaMenu
                tv_harga_menu.text = FilterMenuResponse.hargaMenu.toString()

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(FilterMenuResponse)
                }
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: FilterDataMenuResponse.FilterMenuResponse)
    }
}

