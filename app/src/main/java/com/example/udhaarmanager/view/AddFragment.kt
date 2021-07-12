package com.example.udhaarmanager.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.udhaarmanager.R
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel

class AddFragment : Fragment() {
    val viewModel: TransactionViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding =  inflater.inflate(R.layout.fragment_add, container, false)


        return binding
    }
}

