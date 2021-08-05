package com.example.udhaarmanager.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.udhaarmanager.databinding.FragmentEmailVerificationBinding
import com.example.udhaarmanager.main.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class EmailVerificationFragment : Fragment() {
    private lateinit var binding: FragmentEmailVerificationBinding
    val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEmailVerificationBinding.inflate(layoutInflater, container, false)

        if (auth.currentUser!!.isEmailVerified) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent).also {
                activity?.finish()
            }
        }

        binding.verifyEmail.setOnClickListener {
            verifyUserEmail()
        }
        return binding.root
    }

    private fun verifyUserEmail() {
        auth.currentUser!!.sendEmailVerification()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "email verification link send", Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "error: ${it.message}", Toast.LENGTH_LONG)
                    .show()
            }
    }
}