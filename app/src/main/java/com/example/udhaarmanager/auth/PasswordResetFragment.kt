package com.example.udhaarmanager.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.udhaarmanager.R
import com.example.udhaarmanager.databinding.FragmentPasswordResetBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class PasswordResetFragment : Fragment() {
    private lateinit var binding: FragmentPasswordResetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPasswordResetBinding.inflate(layoutInflater, container, false)

        binding.resetButton.setOnClickListener {
            if (binding.emailInput.text != null) {
                resetPassword(binding.emailInput.text.toString())
            }
        }
        return binding.root
    }

    private fun resetPassword(email: String) {
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Reset Link Send", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.loginFragment)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}