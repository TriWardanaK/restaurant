package com.example.restaurant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant.R
import com.example.restaurant.response.SearchDataMenuResponse
import kotlinx.android.synthetic.main.item_search_menu.view.*

class SearchMenuAdapter(private val listMenu: ArrayList<SearchDataMenuResponse.SearchMenuResponse>)
    : RecyclerView.Adapter<SearchMenuAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMenu[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(SearchMenuResponse: SearchDataMenuResponse.SearchMenuResponse){
            with(itemView){
                Glide.with(itemView.context)
                    .load(SearchMenuResponse.photoMenu)
                    .into(photo_menu)

                tv_nama_menu.text = SearchMenuResponse.namaMenu
                tv_harga_menu.text = SearchMenuResponse.hargaMenu.toString()

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(SearchMenuResponse)
                }
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: SearchDataMenuResponse.SearchMenuResponse)
    }
}

