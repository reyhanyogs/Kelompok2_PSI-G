package com.example.tugasbesarpsi.ui.history

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasbesarpsi.`class`.Doctor
import com.example.tugasbesarpsi.`object`.DAO
import com.example.tugasbesarpsi.adapter.HistoryAdapter
import com.example.tugasbesarpsi.databinding.FragmentHistoryBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.getValue

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val listHistory = ArrayList<Doctor>()
    private val user = Firebase.auth.currentUser
    private lateinit var adapter: HistoryAdapter
    private lateinit var mActivity: Activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistory.layoutManager = LinearLayoutManager(context)
        binding.rvHistory.setHasFixedSize(true)
        adapter = HistoryAdapter(mActivity)
        binding.rvHistory.adapter = adapter
        loadData()
        if (user != null) {
            binding.tvFirst.visibility = View.INVISIBLE
            binding.ivLogo.visibility = View.INVISIBLE
            binding.tvTitle.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.VISIBLE
        } else {
            binding.tvFirst.visibility = View.VISIBLE
            binding.ivLogo.visibility = View.VISIBLE
            binding.tvTitle.visibility = View.INVISIBLE
            binding.rvHistory.visibility = View.INVISIBLE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun loadData() {
        if (user != null) {
            DAO.getHistory(user.uid).addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val doc = data.getValue<Doctor>()
                        listHistory.add(doc!!)
                    }
                    adapter.setItems(listHistory)
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}