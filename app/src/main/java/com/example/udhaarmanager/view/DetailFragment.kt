package com.example.udhaarmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.udhaarmanager.R
import com.example.udhaarmanager.base.BaseFragment
import com.example.udhaarmanager.databinding.FragmentDetailBinding
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, TransactionViewModel>() {
    override val viewModel: TransactionViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        bottomNavOnClickListeners()
    }

    private fun init() = with(binding) {
        detailLayout.titleValue.text = args.transactionDetail.title
        detailLayout.amountValue.text = args.transactionDetail.amount.toString()
        detailLayout.transactionTypeValue.text = args.transactionDetail.transactionType
        detailLayout.tagValue.text = args.transactionDetail.tag
        detailLayout.borrowDateValue.text = args.transactionDetail.borrowDate
        detailLayout.returnDateValue.text = args.transactionDetail.returnDate
        detailLayout.noteValue.text = args.transactionDetail.note
        detailLayout.createdAtValue.text = args.transactionDetail.createdAtDateFormat
    }

    private fun bottomNavOnClickListeners() {
        binding.bottomAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_dashboardFragment)
        }

        binding.bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.deleteButton -> {
                    viewModel.delete(args.transactionDetail.id)
                    findNavController().navigate(R.id.action_detailFragment_to_dashboardFragment)
                    true
                }

                R.id.shareButton -> {
                    true
                }

                R.id.updateButton -> {
                    true
                }
                else -> false
            }
        }
    }
}