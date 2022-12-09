package com.example.tugasbesarpsi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasbesarpsi.R
import com.example.tugasbesarpsi.`class`.Hospital
import com.example.tugasbesarpsi.databinding.ItemCardHospitalAdminBinding

class HomeAdminAdapter: RecyclerView.Adapter<HomeAdminAdapter.CardViewViewHolder>() {
    val listHospital = ArrayList<Hospital>()
    private var onItemClickCallback: OnItemClickCallback? = null
    private var onItemClickCallback2: OnItemClickCallback2? = null

    inner class CardViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardHospitalAdminBinding.bind(itemView)
        fun bind(hospital: Hospital) {
            with(binding) {
                tvName.text = hospital.name
                tvAddress.text = hospital.address
                Glide.with(itemView.context)
                    .load(hospital.avatar)
                    .into(ivPicture)
                btnUpdate.setOnClickListener{onItemClickCallback?.onItemClicked(hospital)}
                btnDelete.setOnClickListener{onItemClickCallback2?.onItemClicked(hospital)}
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeAdminAdapter.CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_card_hospital_admin, parent, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listHospital[position])
    }

    override fun getItemCount(): Int {
        return listHospital.size
    }

    fun setItems(empList: ArrayList<Hospital>){
        listHospital.addAll(empList)
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked (data: Hospital)
    }

    fun setOnItemClickCallback2(onItemClickCallback2: OnItemClickCallback2) {
        this.onItemClickCallback2 = onItemClickCallback2
    }

    interface OnItemClickCallback2 {
        fun onItemClicked (data: Hospital)
    }
}