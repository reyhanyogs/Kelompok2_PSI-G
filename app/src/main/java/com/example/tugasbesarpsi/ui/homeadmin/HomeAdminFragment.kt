package com.example.tugasbesarpsi.ui.homeadmin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasbesarpsi.R
import com.example.tugasbesarpsi.`class`.Hospital
import com.example.tugasbesarpsi.`object`.DAO
import com.example.tugasbesarpsi.adapter.HomeAdapter
import com.example.tugasbesarpsi.adapter.HomeAdminAdapter
import com.example.tugasbesarpsi.databinding.FragmentHomeAdminBinding
import com.example.tugasbesarpsi.ui.update.UpdateFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import es.dmoral.toasty.Toasty


class HomeAdminFragment : Fragment() {
    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeAdminAdapter
    private lateinit var mActivity: Activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCard.setHasFixedSize(true)
        binding.rvCard.layoutManager = LinearLayoutManager(context)
        adapter = HomeAdminAdapter()
        binding.rvCard.adapter = adapter
        loadData()
        adapter.setOnItemClickCallback(object: HomeAdminAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Hospital) {
                UpdateFragment.EXTRA_HOSPITAL = data
                view.findNavController().navigate(R.id.action_navigation_home_admin_to_anvigation_update)
            }
        })
        adapter.setOnItemClickCallback2(object: HomeAdminAdapter.OnItemClickCallback2{
            override fun onItemClicked(data: Hospital) {
                DAO.deleteHospital(data.idHospital)
                Toasty.success(mActivity, "Hospital successfully deleted", Toast.LENGTH_SHORT, true).show()
                loadData()
            }
        })
    }

    private fun loadData() {
        DAO.getHospital().addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val hos = ArrayList<Hospital>()
                for (data in snapshot.children) {
                    val hospital = data.getValue<Hospital>()
                    hos.add(hospital!!)
                }
                adapter.listHospital.clear()
                adapter.setItems(hos)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}