package com.example.udhaarmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.udhaarmanager.R
import com.example.udhaarmanager.base.BaseFragment
import com.example.udhaarmanager.databinding.FragmentAddBinding
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel
import com.example.udhaarmanager.model.Transaction
import com.example.udhaarmanager.util.Constants
import com.example.udhaarmanager.util.transformIntoDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.Double.parseDouble
import java.util.*

@AndroidEntryPoint
class AddFragment :
    BaseFragment<FragmentAddBinding, TransactionViewModel>() {
    override val viewModel: TransactionViewModel by viewModels()
    private val args: AddFragmentArgs by navArgs()
    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionRef = auth.currentUser?.email.toString()
    private var createdAt: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.loadDataExecute) {
            loadData()
        }
        initViews()
        bottomNavOnClickListener()
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

        //Adapters
        binding.addTransactionLayout.transactionTypeAdd.setAdapter(transactionTypeAdapter)
        binding.addTransactionLayout.tagAdd.setAdapter(transactionTagAdapter)

        //Date Picker
        binding.addTransactionLayout.whenAdd.transformIntoDatePicker(
            requireContext(),
            "dd/MM/yyyy",
            minDate = null,
            maxDate = Date()
        )

        binding.addTransactionLayout.returnAdd.transformIntoDatePicker(
            requireContext(),
            "dd/MM/yyyy",
            minDate = Date(),
            maxDate = null
        )
    }

    private fun getAddTransactionData(): Transaction = binding.addTransactionLayout.let {
        val name = it.name.text.toString()
        val amount = parseDouble(it.amountAdd.text.toString())
        val transactionType = it.transactionTypeAdd.text.toString()
        val tag = it.tagAdd.text.toString()
        val borrowDate = it.whenAdd.text.toString()
        val returnDate = it.returnAdd.text.toString()
        val note = it.noteAdd.text.toString()
        createdAt = if (args.transaction.createdAt == null) {
            System.currentTimeMillis()
        } else {
            args.transaction.createdAt!!.toLong()
        }
        val number = args.transactor.number.toString()

        return Transaction(
            name,
            amount,
            transactionType,
            tag,
            borrowDate,
            returnDate,
            note,
            createdAt,
            number
        )
    }

    private fun loadData() {
        val bind = binding.addTransactionLayout
        val arg = args.transaction
        try {
            bind.name.setText(arg.title)
            bind.amountAdd.setText(args.transaction.amount.toString())
            bind.transactionTypeAdd.setText(arg.transactionType)
            bind.tagAdd.setText(arg.tag)
            bind.whenAdd.setText(arg.borrowDate)
            bind.returnAdd.setText(arg.returnDate)
            bind.noteAdd.setText(arg.note)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun bottomNavOnClickListener() {
        binding.bottomAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.saveButtonMenu -> {
                    val transaction = getAddTransactionData()
                    db.collection(collectionRef).document(args.transactor.number.toString())
                        .collection(args.transactor.name.toString())
                        .document(transaction.createdAt.toString())
                        .set(transaction)
                        .also {
                            val action =
                                AddFragmentDirections.actionAddFragmentToPersonTransactFragment(args.transactor)
                            findNavController().navigate(action)
                        }
                    true
                }
                else -> false
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddBinding = FragmentAddBinding.inflate(inflater, container, false)
}

