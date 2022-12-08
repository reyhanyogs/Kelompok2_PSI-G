package com.example.tugasbesarpsi.ui.add

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.tugasbesarpsi.R
import com.example.tugasbesarpsi.`class`.Doctor
import com.example.tugasbesarpsi.`class`.Hospital
import com.example.tugasbesarpsi.`class`.LoadingDialog
import com.example.tugasbesarpsi.`object`.DAO
import com.example.tugasbesarpsi.databinding.FragmentAddBinding
import com.google.android.gms.tasks.Task
import es.dmoral.toasty.Toasty
import java.util.*

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var avatarHospital: Uri
    private lateinit var avatarDoctor: Uri
    private lateinit var avatarDoctor2: Uri
    private lateinit var id: String
    private lateinit var mActivity: Activity
    var resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            avatarHospital = it
            binding.ivPicture.setImageURI(it)
            binding.ivPicture.setPadding(0,0,0,0)
        }
    }
    var resultLauncher2 = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            avatarDoctor = it
            binding.ivPictureDoctor.setImageURI(it)
            binding.ivPictureDoctor.setPadding(0,0,0,0)
        }
    }
    var resultLauncher3 = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            avatarDoctor2 = it
            binding.ivPictureDoctor2.setImageURI(it)
            binding.ivPictureDoctor2.setPadding(0,0,0,0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivPicture.setOnClickListener {
            resultLauncher.launch("image/*")
        }
        binding.ivPictureDoctor.setOnClickListener {
            resultLauncher2.launch("image/*")
        }
        binding.ivPictureDoctor2.setOnClickListener {
            resultLauncher3.launch("image/*")
        }
        binding.btnAdd.setOnClickListener {
            check()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun check() {
        var isInputted: Boolean
        if (binding.ivPicture.drawable.constantState == ContextCompat.getDrawable(requireContext(), R.drawable.upload_logo)?.constantState
            || binding.edtName.text.isEmpty() || binding.edtAddress.text.isEmpty() || binding.edtDoctorName.text.isEmpty()
            || binding.edtDoctorAge.text.isEmpty() || binding.edtDoctorGender.text.isEmpty()
            || binding.edtDoctorSpecialist.text.isEmpty()
            || binding.ivPictureDoctor.drawable.constantState == ContextCompat.getDrawable(requireContext(), R.drawable.upload_logo)?.constantState) {
            isInputted = false
            if (binding.edtName.text.isEmpty()) {
                binding.edtName.error = "This field can not be blank"
            }
            if (binding.edtAddress.text.isEmpty()) {
                binding.edtAddress.error = "This field can not be blank"
            }
            if (binding.edtDoctorName.text.isEmpty()) {
                binding.edtDoctorName.error = "This field can not be blank"
            }
            if (binding.edtDoctorAge.text.isEmpty()) {
                binding.edtDoctorAge.error = "This field can not be blank"
            }
            if (binding.edtDoctorGender.text.isEmpty()) {
                binding.edtDoctorGender.error = "This field can not be blank"
            }
            if (binding.edtDoctorSpecialist.text.isEmpty()) {
                binding.edtDoctorSpecialist.error = "This field can not be blank"
            }
            Toasty.info(mActivity, "Please fill all the requirement", Toast.LENGTH_SHORT, true).show()
        }
        if (binding.ivPicture.drawable.constantState == ContextCompat.getDrawable(requireContext(), R.drawable.upload_logo)?.constantState) {
            isInputted = false
            Toasty.info(mActivity, "Please upload hospital image", Toast.LENGTH_SHORT, true).show()
        }
        if (binding.ivPictureDoctor.drawable.constantState == ContextCompat.getDrawable(requireContext(), R.drawable.upload_logo)?.constantState) {
            isInputted = false
            Toasty.info(mActivity, "Please upload doctor image", Toast.LENGTH_SHORT, true).show()
        } else {
            isInputted = true
        }

        if (isInputted) {
            val loadingDialog = LoadingDialog(mActivity)
            loadingDialog.startLoadingDialog()
            val doctorList = ArrayList<Doctor>()
            var url: String
            id = UUID.randomUUID().toString()
            DAO.uploadImage(avatarHospital, id).addOnSuccessListener { p0 ->
                val downloadUrl: Task<Uri> = p0!!.storage.downloadUrl
                downloadUrl.addOnCompleteListener { p0 ->
                    url = ("https://" + p0.result.encodedAuthority
                            + p0.result.encodedPath
                            .toString() + "?alt=media&token="
                            + p0.result.getQueryParameters("token")[0])
                    id = UUID.randomUUID().toString()
                    DAO.uploadImage(avatarDoctor, id).addOnSuccessListener { p0 ->
                        val downloadUrl2: Task<Uri> = p0!!.storage.downloadUrl
                        downloadUrl2.addOnCompleteListener { p0 ->
                            val url2 = ("https://" + p0.result.encodedAuthority
                                    + p0.result.encodedPath
                                .toString() + "?alt=media&token="
                                    + p0.result.getQueryParameters("token")[0])
                            val doctor = Doctor(
                                url2,
                                binding.edtDoctorName.text.toString(),
                                binding.edtDoctorAge.text.toString(),
                                binding.edtDoctorGender.text.toString(),
                                binding.edtDoctorSpecialist.text.toString()
                            )
                            doctorList.add(doctor)
                            if (binding.edtDoctorName2.text.isNotEmpty() && binding.edtDoctorAge2.text.isNotEmpty() && binding.edtDoctorGender2.text.isNotEmpty()
                                && binding.edtDoctorSpecialist2.text.isNotEmpty() &&
                                binding.ivPictureDoctor2.drawable.constantState != ContextCompat.getDrawable(requireContext(), R.drawable.upload_logo)?.constantState) {
                                id = UUID.randomUUID().toString()
                                DAO.uploadImage(avatarDoctor2, id).addOnSuccessListener { p0 ->
                                    val downloadUrl3: Task<Uri> = p0!!.storage.downloadUrl
                                    downloadUrl3.addOnCompleteListener { p0 ->
                                        val url3 = ("https://" + p0.result.encodedAuthority
                                                + p0.result.encodedPath
                                                .toString() + "?alt=media&token="
                                                + p0.result.getQueryParameters("token")[0])
                                        val doctor2 = Doctor(
                                            url3,
                                            binding.edtDoctorName2.text.toString(),
                                            binding.edtDoctorAge2.text.toString(),
                                            binding.edtDoctorGender2.text.toString(),
                                            binding.edtDoctorSpecialist2.text.toString()
                                        )
                                        doctorList.add(doctor2)
                                    }
                                }
                            }
                            val hospital = Hospital(url, binding.edtName.text.toString(), binding.edtAddress.text.toString())
                            hospital.doctor = doctorList.toList()
                            DAO.addHospital(hospital)
                            Log.d("Link Download", url)
                            binding.ivPicture.setImageDrawable(null)
                            binding.edtName.text.clear()
                            binding.edtAddress.text.clear()
                            binding.edtDoctorName.text.clear()
                            binding.edtDoctorAge.text.clear()
                            binding.edtDoctorGender.text.clear()
                            binding.edtDoctorSpecialist.text.clear()
                            binding.ivPictureDoctor.setImageDrawable(null)
                            if (binding.edtDoctorName2.text.isNotEmpty() && binding.edtDoctorAge2.text.isNotEmpty() && binding.edtDoctorGender2.text.isNotEmpty()
                                && binding.edtDoctorSpecialist2.text.isNotEmpty() && binding.ivPictureDoctor2.drawable == null) {
                                binding.edtDoctorName2.text.clear()
                                binding.edtDoctorAge2.text.clear()
                                binding.edtDoctorGender2.text.clear()
                                binding.edtDoctorSpecialist2.text.clear()
                                binding.ivPictureDoctor2.setImageDrawable(null)
                            }
                            loadingDialog.dismissDialog()
                            Toasty.success(mActivity, "Hospital successfully registered", Toast.LENGTH_SHORT, true).show()
                        }
                    }
                }
            }
        }
    }
}