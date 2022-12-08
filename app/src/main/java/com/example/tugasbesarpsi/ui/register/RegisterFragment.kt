package com.example.tugasbesarpsi.ui.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tugasbesarpsi.HomeActivity
import com.example.tugasbesarpsi.`class`.LoadingDialog
import com.example.tugasbesarpsi.`class`.Person
import com.example.tugasbesarpsi.`object`.DAO
import com.example.tugasbesarpsi.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var mActivity: Activity
    private lateinit var person: Person

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isInputted: Boolean
        binding.btnRegister.setOnClickListener {
            if (binding.edtEmail.text.isEmpty()) {
                isInputted = false
                binding.edtEmail.error = "This field can not be blank"
            } else {
                isInputted = true
            }
            if (binding.edtNamaLengkap.text.isEmpty()) {
                isInputted = false
                binding.edtNamaLengkap.error = "This field can not be blank"
            } else {
                isInputted = true
            }
            if (binding.edtPhone.text.isEmpty()) {
                isInputted = false
                binding.edtPhone.error = "This field can not be blank"
            } else {
                isInputted = true
            }
            if (binding.edtPassword.text.isEmpty()) {
                isInputted = false
                binding.edtPassword.error = "This field can not be blank"
            }
            if (isInputted) {
                register(binding.edtNamaLengkap.text.toString(), binding.edtEmail.text.toString(),
                        binding.edtPassword.text.toString())
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        val loadingDialog = LoadingDialog(mActivity)
        loadingDialog.startLoadingDialog()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(mActivity) { task ->
            if (task.isSuccessful) {
                val user = Firebase.auth.currentUser
                if (user!=null) {
                    val profileUpdate = userProfileChangeRequest {
                        displayName = name
                    }
                    user.updateProfile(profileUpdate)
                        .addOnCompleteListener {
                            person = Person(binding.edtEmail.text.toString(),
                                            binding.edtNamaLengkap.text.toString(),
                                            binding.edtPhone.text.toString(),
                                            binding.edtPassword.text.toString())
                            DAO.addPerson(person, user.uid)
                            Toasty.success(mActivity, "Account successfully registered", Toast.LENGTH_SHORT, true).show()
                            loadingDialog.dismissDialog()
                            startActivity(Intent(activity, HomeActivity::class.java))
                        }
                } else {
                    loadingDialog.dismissDialog()
                    Toasty.error(mActivity, "Registration failed", Toast.LENGTH_SHORT, true).show()
                }
            } else {
                loadingDialog.dismissDialog()
                val error: String = task.exception?.localizedMessage!!
                Toasty.error(mActivity, error, Toast.LENGTH_SHORT, true).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}