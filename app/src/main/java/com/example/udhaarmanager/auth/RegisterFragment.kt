package com.example.udhaarmanager.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.udhaarmanager.R
import com.example.udhaarmanager.databinding.FragmentRegisterBinding
import com.example.udhaarmanager.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.register.setOnClickListener {
            validateData()
        }

        return binding.root
    }

    private fun validateData() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        when {
            email.isEmpty() -> {
                binding.email.error = "Email Can't Be Empty"
            }

            password.isEmpty() -> {
                binding.password.error = "Password Can't Be Empty"
            }
            confirmPassword.isEmpty() -> {
                binding.confirmPassword.error = "Password Can't Be Empty"
            }

            password.isNotEmpty() -> {
                if (password != confirmPassword)
                    binding.password.error = "Password Don't match"
                else
                    register(email, password)
            }
        }
    }

    private fun register(email: String, password: String) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    val currentUser: FirebaseUser = it.result!!.user!!
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
