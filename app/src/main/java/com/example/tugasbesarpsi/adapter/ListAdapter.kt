package com.example.tugasbesarpsi.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesarpsi.R

class ListAdapter: RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    val list = arrayOf("Address", "Edit Profile", "Payment Method", "Ask & Help", "Log out")

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvItem: TextView = itemView.findViewById(R.id.tv_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
        val currentData = list[position]
        holder.tvItem.text = currentData
        if (currentData.equals("Log out")) {
            holder.tvItem.setTextColor(Color.parseColor("#FF0000"))
        }
        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(list[holder.adapterPosition])}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }
}