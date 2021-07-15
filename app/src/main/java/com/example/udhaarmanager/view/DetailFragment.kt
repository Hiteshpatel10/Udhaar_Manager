package com.example.udhaarmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.udhaarmanager.base.BaseFragment
import com.example.udhaarmanager.databinding.FragmentDetailBinding
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, TransactionViewModel>() {

    private val args: DetailFragmentArgs by navArgs()
    override val viewModel: TransactionViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding){
        detailLayout.titleValue.text = args.transactionDetail.title
        detailLayout.amountValue.text =args.transactionDetail.amount.toString()
        detailLayout.transactionTypeValue.text = args.transactionDetail.transactionType
        detailLayout.tagValue.text = args.transactionDetail.tag
        detailLayout.whenValue.text = args.transactionDetail.date
        detailLayout.noteValue.text = args.transactionDetail.note
        detailLayout.createdAtValue.text = args.transactionDetail.createdAtDateFormat
    }
}