package com.example.tugasbesarpsi.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasbesarpsi.HomeActivity
import com.example.tugasbesarpsi.RegisterLoginActivity
import com.example.tugasbesarpsi.`class`.Person
import com.example.tugasbesarpsi.`object`.DAO
import com.example.tugasbesarpsi.adapter.ListAdapter
import com.example.tugasbesarpsi.databinding.FragmentProfileBinding
import com.google.firebase.database.ktx.getValue
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Firebase.auth.currentUser
        if (user != null) {
            binding.ivLogo.visibility = View.INVISIBLE
            binding.tvFirst.visibility = View.INVISIBLE
            binding.tvLoginFirst.visibility = View.INVISIBLE
            binding.tvFirst2.visibility = View.INVISIBLE
            binding.tvRegisterFirst.visibility = View.INVISIBLE
            binding.ivProfile.visibility = View.VISIBLE
            binding.tvNamaLengkap.visibility = View.VISIBLE
            binding.tvNomor.visibility = View.VISIBLE
            binding.line.visibility = View.VISIBLE
            binding.rvList.visibility = View.VISIBLE
            val listAdapter = ListAdapter()
            binding.rvList.setHasFixedSize(true)
            binding.rvList.layoutManager = LinearLayoutManager(context)
            binding.rvList.adapter = listAdapter
            loadData()
            listAdapter.setOnItemClickCallback(object: ListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: String) {
                    if (data.equals("Log out")) {
                        Firebase.auth.signOut()
                        startActivity(Intent(context, HomeActivity::class.java))
                    }
                }
            })
        } else {
            binding.ivLogo.visibility = View.VISIBLE
            binding.tvFirst.visibility = View.VISIBLE
            binding.tvLoginFirst.visibility = View.VISIBLE
            binding.tvFirst2.visibility = View.VISIBLE
            binding.tvRegisterFirst.visibility = View.VISIBLE
            binding.ivProfile.visibility = View.INVISIBLE
            binding.tvNamaLengkap.visibility = View.INVISIBLE
            binding.tvNomor.visibility = View.INVISIBLE
            binding.line.visibility = View.INVISIBLE
            binding.rvList.visibility = View.INVISIBLE
            binding.tvLoginFirst.setOnClickListener {
                RegisterLoginActivity.STATUS = 1
                startActivity(Intent(activity, RegisterLoginActivity::class.java))
            }
            binding.tvRegisterFirst.setOnClickListener {
                RegisterLoginActivity.STATUS = 0
                startActivity(Intent(activity, RegisterLoginActivity::class.java))
            }
        }
    }

    fun loadData() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            DAO.getSpecificPerson(user.uid).addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val personList = ArrayList<Person>()
                    val person = snapshot.getValue<Person>()
                    personList.add(person!!)
                    for (person in personList) {
                        binding.tvNamaLengkap.text = person.name
                        binding.tvNomor.text = person.phoneNumber
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}