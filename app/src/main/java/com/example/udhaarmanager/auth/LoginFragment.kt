package com.example.udhaarmanager.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.udhaarmanager.R
import com.example.udhaarmanager.databinding.FragmentLoginBinding
import com.example.udhaarmanager.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.login.setOnClickListener {
            login()
        }

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.forgotDetails.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_passwordResetFragment)
        }
        return binding.root
    }

    private fun login() {
        when {
            TextUtils.isEmpty(binding.email.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    requireContext(),
                    "enter email",
                    Toast.LENGTH_LONG
                ).show()
            }
            TextUtils.isEmpty(binding.password.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    requireContext(),
                    "enter password",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                val email: String = binding.email.text.toString().trim { it <= ' ' }
                val pass: String = binding.password.text.toString().trim { it <= ' ' }

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {
                            val currentUser: FirebaseUser = it.result!!.user!!

                            Toast.makeText(
                                requireContext(),
                                "Welcome ${currentUser.email}",
                                Toast.LENGTH_LONG
                            ).show()

                            if (currentUser.isEmailVerified) {
                                val intent = Intent(requireContext(), MainActivity::class.java)
                                startActivity(intent).also {
                                    activity?.finish()
                                }
                            } else {
                                findNavController().navigate(R.id.emailVerificationFragment)
                            }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            requireContext(),
                            "${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }
}