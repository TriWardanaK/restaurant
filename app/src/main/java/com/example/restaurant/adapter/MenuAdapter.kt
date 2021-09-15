package com.example.restaurant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant.R
import com.example.restaurant.response.DataMenuResponse
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter(private val listMenu: ArrayList<DataMenuResponse.MenuResponse>)
    : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMenu[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(MenuResponse: DataMenuResponse.MenuResponse){
            with(itemView){
                Glide.with(itemView.context)
                    .load(MenuResponse.photoMenu)
                    .into(photo_menu)

                tv_nama_menu.text = MenuResponse.namaMenu
                tv_harga_menu.text = MenuResponse.hargaMenu.toString()

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(MenuResponse)
                }
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataMenuResponse.MenuResponse)
    }
}

