package com.example.udhaarmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.udhaarmanager.base.BaseFragment
import com.example.udhaarmanager.databinding.FragmentDetailBinding
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, TransactionViewModel>() {
    override val viewModel: TransactionViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}