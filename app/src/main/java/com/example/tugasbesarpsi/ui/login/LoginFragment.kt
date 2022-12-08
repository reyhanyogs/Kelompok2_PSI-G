package com.example.tugasbesarpsi.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tugasbesarpsi.AdminHomeActivity
import com.example.tugasbesarpsi.HomeActivity
import com.example.tugasbesarpsi.`class`.LoadingDialog
import com.example.tugasbesarpsi.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: Activity
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(activity, HomeActivity::class.java))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isInputted: Boolean
        binding.btnLogin.setOnClickListener {
            if (binding.edtEmail.text.isEmpty()) {
                isInputted = false
                binding.edtEmail.error = "This field can not be blank"
            } else {
                isInputted = true
            }
            if (binding.edtPassword.text.isEmpty()) {
                isInputted = false
                binding.edtPassword.error = "This field can not be blank"
            } else {
                isInputted = true
            }
            if (isInputted) {
                login(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun login(email: String, password: String) {
        val loadingDialog = LoadingDialog(mActivity)
        loadingDialog.startLoadingDialog()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                if (task.result.user != null) {
                    if (binding.edtEmail.text.toString() == "admin@gmail.com"
                        && binding.edtPassword.text.toString() == "rootadmin"
                    ) {
                        loadingDialog.dismissDialog()
                        Toasty.success(mActivity, "Login Successful", Toast.LENGTH_SHORT, true).show()
                        startActivity(Intent(activity, AdminHomeActivity::class.java))
                        } else {
                        loadingDialog.dismissDialog()
                        Toasty.success(mActivity, "Login Successful", Toast.LENGTH_SHORT, true).show()
                        startActivity(Intent(activity, HomeActivity::class.java))
                    }
                } else {
                    loadingDialog.dismissDialog()
                    Toasty.error(mActivity, "Login Failed", Toast.LENGTH_SHORT, true).show()
                }
            } else {
                loadingDialog.dismissDialog()
                Toasty.error(mActivity, "Login Failed", Toast.LENGTH_SHORT, true).show()
            }
        }
    }
}