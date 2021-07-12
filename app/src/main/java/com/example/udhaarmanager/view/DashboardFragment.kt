package com.example.udhaarmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.udhaarmanager.R
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel
import com.example.udhaarmanager.model.Transaction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    val viewModel: TransactionViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_dashboard, container, false)

        binding.text.text = viewModel._transaction
        return binding
    }
}