package com.example.tugasbesarpsi.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasbesarpsi.R
import com.example.tugasbesarpsi.`class`.Hospital
import com.example.tugasbesarpsi.`object`.DAO
import com.example.tugasbesarpsi.adapter.HomeAdapter
import com.example.tugasbesarpsi.databinding.FragmentHomeBinding
import com.example.tugasbesarpsi.ui.detail.DetailFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeAdapter
    private lateinit var mActivity: Activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCard.setHasFixedSize(true)
        binding.rvCard.layoutManager = LinearLayoutManager(context)
        adapter = HomeAdapter()
        binding.rvCard.adapter = adapter
        loadData()
        adapter.setOnItemClickCallback(object: HomeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Hospital) {
                DetailFragment.EXTRA_HOSPITAL = data
                view.findNavController().navigate(R.id.action_navigation_home_to_navigation_detail)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData() {
        DAO.getHospital().addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val hos = ArrayList<Hospital>()
                for (data in snapshot.children) {
                    val hospital = data.getValue<Hospital>()
                    hos.add(hospital!!)
                    Log.d("Cek Data", hospital.name)
                }
                adapter.setItems(hos)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}