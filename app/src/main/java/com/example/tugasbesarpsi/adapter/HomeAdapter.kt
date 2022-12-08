package com.example.tugasbesarpsi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasbesarpsi.R
import com.example.tugasbesarpsi.`class`.Hospital
import com.example.tugasbesarpsi.databinding.ItemCardHospitalBinding

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.CardViewViewHolder>() {
    val listHospital = ArrayList<Hospital>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class CardViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardHospitalBinding.bind(itemView)
        fun bind(hospital: Hospital) {
            with(binding) {
                tvName.text = hospital.name
                tvAddress.text = hospital.address
                Glide.with(itemView.context)
                    .load(hospital.avatar)
                    .into(ivPicture)
            }
            itemView.setOnClickListener{onItemClickCallback?.onItemClicked(hospital)}
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeAdapter.CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_card_hospital, parent, false)
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
}