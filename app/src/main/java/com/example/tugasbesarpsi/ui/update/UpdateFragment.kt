package com.example.tugasbesarpsi.ui.update

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.tugasbesarpsi.R
import com.example.tugasbesarpsi.`class`.Doctor
import com.example.tugasbesarpsi.`class`.Hospital
import com.example.tugasbesarpsi.`class`.LoadingDialog
import com.example.tugasbesarpsi.`object`.DAO
import com.example.tugasbesarpsi.databinding.FragmentHomeAdminBinding
import com.example.tugasbesarpsi.databinding.FragmentHomeBinding
import com.example.tugasbesarpsi.databinding.FragmentUpdateBinding
import com.google.android.gms.tasks.Task
import es.dmoral.toasty.Toasty
import java.util.*
import kotlin.collections.HashMap


class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: Activity
    private lateinit var avatarHospital: Uri
    private lateinit var id: String
    var resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            avatarHospital = it
            binding.ivPicture.setImageURI(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtName.setText(EXTRA_HOSPITAL.name)
        binding.edtAddress.setText(EXTRA_HOSPITAL.address)
        Glide.with(this)
            .load(EXTRA_HOSPITAL.avatar)
            .into(binding.ivPicture)
        binding.ivPicture.setOnClickListener {
            resultLauncher.launch("image/*")
        }
        binding.btnBack.setOnClickListener {
            view.findNavController().navigate(R.id.action_anvigation_update_to_navigation_home_admin)
        }
        binding.btnUpdate.setOnClickListener {
            check()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun check() {
        var isInputted: Boolean
        if ( binding.edtName.text.isEmpty() || binding.edtAddress.text.isEmpty()) {
            isInputted = false
            if (binding.edtName.text.isEmpty()) {
                binding.edtName.error = "This field can not be blank"
            }
            if (binding.edtAddress.text.isEmpty()) {
                binding.edtAddress.error = "This field can not be blank"
            }
            Toasty.info(mActivity, "Please fill all the requirement", Toast.LENGTH_SHORT, true).show()
        } else {
            isInputted = true
        }

        if (isInputted) {
            val loadingDialog = LoadingDialog(mActivity)
            loadingDialog.startLoadingDialog()
            var url: String
            id = UUID.randomUUID().toString()
            if (this::avatarHospital.isInitialized) {
                DAO.uploadImage(avatarHospital, id).addOnSuccessListener { p0 ->
                    val downloadUrl: Task<Uri> = p0!!.storage.downloadUrl
                    downloadUrl.addOnCompleteListener { p0 ->
                        url = ("https://" + p0.result.encodedAuthority
                                + p0.result.encodedPath
                            .toString() + "?alt=media&token="
                                + p0.result.getQueryParameters("token")[0])
                        val hashmap = HashMap<String, Any>()
                        hashmap.put("name", binding.edtName.text.toString())
                        hashmap.put("address", binding.edtAddress.text.toString())
                        hashmap.put("avatar", url)
                        DAO.updateHospital(EXTRA_HOSPITAL.idHospital, hashmap)
                        binding.ivPicture.setImageDrawable(null)
                        binding.edtName.text.clear()
                        binding.edtAddress.text.clear()
                        loadingDialog.dismissDialog()
                        view?.findNavController()?.navigate(R.id.action_anvigation_update_to_navigation_home_admin)
                        Toasty.success(mActivity, "Hospital successfully updated", Toast.LENGTH_SHORT, true).show()
                    }
                }
            } else {
                val hashmap = HashMap<String, Any>()
                hashmap.put("name", binding.edtName.text.toString())
                hashmap.put("address", binding.edtAddress.text.toString())
                DAO.updateHospital(EXTRA_HOSPITAL.idHospital, hashmap)
                binding.ivPicture.setImageDrawable(null)
                binding.edtName.text.clear()
                binding.edtAddress.text.clear()
                loadingDialog.dismissDialog()
                view?.findNavController()?.navigate(R.id.action_anvigation_update_to_navigation_home_admin)
                Toasty.success(mActivity, "Hospital successfully updated", Toast.LENGTH_SHORT, true).show()
            }
        }
    }

    companion object {
        var EXTRA_HOSPITAL = Hospital()
    }
}