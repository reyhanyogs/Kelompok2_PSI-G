package com.example.tugasbesarpsi.ui.detail

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tugasbesarpsi.R
import com.example.tugasbesarpsi.`class`.Doctor
import com.example.tugasbesarpsi.`class`.Hospital
import com.example.tugasbesarpsi.`object`.DAO
import com.example.tugasbesarpsi.adapter.DetailAdapter
import com.example.tugasbesarpsi.databinding.FragmentDetailBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: Activity
    private lateinit var adapter: DetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(view)
            .load(EXTRA_HOSPITAL.avatar)
            .into(binding.ivHospital)
        binding.tvHospitalName.text = EXTRA_HOSPITAL.name
        binding.tvHospitalAddress.text = EXTRA_HOSPITAL.address
        loadData()
        val user = Firebase.auth.currentUser
        adapter.setOnItemClickCallback(object: DetailAdapter.OnItemClickCallback{
            override fun onItemClicked(doctor: Doctor) {
                if (user != null) {
                    DAO.addHistory(doctor, user.uid)
                    Toasty.success(mActivity, "Booking succeed", Toast.LENGTH_SHORT, true).show()
                } else {
                    Toasty.info(mActivity, "Please login first", Toast.LENGTH_SHORT, true).show()
                }
            }
        })
        binding.btnBack.setOnClickListener {
            view.findNavController().navigate(R.id.action_navigation_detail_to_navigation_home)
        }
    }

    private fun loadData() {
        binding.rvCard.setHasFixedSize(true)
        binding.rvCard.layoutManager = LinearLayoutManager(context)
        adapter = DetailAdapter(mActivity, EXTRA_HOSPITAL.doctor)
        binding.rvCard.adapter = adapter

    }

    companion object {
        var EXTRA_HOSPITAL = Hospital()
    }
}