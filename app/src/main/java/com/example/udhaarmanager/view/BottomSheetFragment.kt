package com.example.udhaarmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.udhaarmanager.R
import com.example.udhaarmanager.databinding.FragmentBotttomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class BottomSheetFragment : BottomSheetDialogFragment() {

    private val args: BottomSheetFragmentArgs by navArgs()
    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionRef = auth.currentUser?.email.toString()
    private lateinit var binding: FragmentBotttomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBotttomSheetBinding.inflate(layoutInflater, container, false)

        val displayText = "Delete  ${args.transactor.name}"
        binding.textBottomSheet.text = displayText

        binding.deleteBottomSheet.setOnClickListener {
            db.collection(collectionRef).document(args.transactor.number.toString()).delete().also {
                findNavController().navigate(R.id.dashboardFragment)
            }
        }

        binding.cancelBottomSheet.setOnClickListener {
            findNavController().navigate(R.id.dashboardFragment)
        }
        return binding.root
    }

}