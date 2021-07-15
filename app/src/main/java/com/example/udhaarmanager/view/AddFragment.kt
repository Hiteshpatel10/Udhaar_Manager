package com.example.udhaarmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.udhaarmanager.R
import com.example.udhaarmanager.base.BaseFragment
import com.example.udhaarmanager.databinding.FragmentAddBinding
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel
import com.example.udhaarmanager.model.Transaction
import com.example.udhaarmanager.util.transformIntoDatePicker
import com.example.udhaarmanager.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Double.parseDouble
import java.util.*

@AndroidEntryPoint
class AddFragment :
    BaseFragment<FragmentAddBinding, TransactionViewModel>() {
    override val viewModel: TransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        val transactionTypeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_autocomplete_layout,
            Constants.transactionType
        )

        val transactionTagAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_autocomplete_layout,
            Constants.transactionTags
        )

        with(binding){

            //Adapters
            addTransactionLayout.transactionTypeAdd.setAdapter(transactionTypeAdapter)
            addTransactionLayout.tagAdd.setAdapter(transactionTagAdapter)

            //Date Picker
            addTransactionLayout.whenAdd.transformIntoDatePicker(
                requireContext(),
                "dd/MM/yyyy",
                Date()
            )

            submitButton.setOnClickListener {
                viewModel.insert(getAddTransactionData())
                findNavController().navigate(R.id.action_addFragment_to_dashboardFragment)
            }
        }
    }

    private fun getAddTransactionData(): Transaction = binding.addTransactionLayout.let{
        val title = it.titleAdd.text.toString()
        val amount = parseDouble(it.amountAdd.text.toString())
        val transactionType = it.transactionTypeAdd.text.toString()
        val tag = it.tagAdd.text.toString()
        val date = it.whenAdd.text.toString()
        val note = it.noteAdd.text.toString()

        return Transaction(title,amount,transactionType,tag,date,note)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddBinding = FragmentAddBinding.inflate(inflater, container, false)
}

