package com.example.tugasbesarpsi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasbesarpsi.R
import com.example.tugasbesarpsi.`class`.Doctor
import com.example.tugasbesarpsi.databinding.ItemCardDoctorBinding

class DetailAdapter(val context: Context, val listDoctor: List<Doctor>): RecyclerView.Adapter<DetailAdapter.CardViewViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class CardViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardDoctorBinding.bind(itemView)
        fun bind(doctor: Doctor) {
            with(binding) {
                tvName.text = doctor.name
                tvAge.text = context.resources.getString(R.string.age, doctor.age)
                tvGender.text = doctor.gender
                tvSpecialist.text = doctor.specialist
                btnOrder.setOnClickListener {onItemClickCallback?.onItemClicked(doctor)}
                Glide.with(itemView.context)
                    .load(doctor.avatar)
                    .into(ivPicture)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailAdapter.CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_card_doctor, parent, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listDoctor[position])
    }

    override fun getItemCount(): Int {
        return listDoctor.size
    }



    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(doctor: Doctor)
    }
}