package com.example.tugasbesarpsi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasbesarpsi.R
import com.example.tugasbesarpsi.`class`.Doctor
import com.example.tugasbesarpsi.databinding.ItemRowHistoryBinding

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    val listDoctor = ArrayList<Doctor>()

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowHistoryBinding.bind(itemView)
        fun bind(doctor: Doctor) {
            with (binding) {
                tvName.text = doctor.name
                tvAge.text = doctor.age
                tvGender.text = doctor.gender
                tvSpecialist.text = doctor.specialist
                Glide.with(itemView)
                    .load(doctor.avatar)
                    .into(ivProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_history, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ListViewHolder, position: Int) {
        holder.bind(listDoctor[position])
    }

    override fun getItemCount(): Int {
        return listDoctor.size
    }

    fun setItems(docList: ArrayList<Doctor>){
        listDoctor.addAll(docList)
    }
}