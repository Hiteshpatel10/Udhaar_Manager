package com.example.udhaarmanager.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.udhaarmanager.R
import com.example.udhaarmanager.adapter.TransactionAdapter
import com.example.udhaarmanager.auth.AuthActivity
import com.example.udhaarmanager.base.BaseFragment
import com.example.udhaarmanager.databinding.FragmentDashboardBinding
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel
import com.example.udhaarmanager.model.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, TransactionViewModel>(),
    TransactionAdapter.ITransactionListener {
    override val viewModel: TransactionViewModel by viewModels()
    private lateinit var adapter: TransactionAdapter
    private lateinit var auth: FirebaseAuth

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

    override fun onItemClicked(transaction: Transaction) {
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(transaction)
        findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TransactionAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        button()
        adapterRecyclerView()
        bottomNavOnClickListener()
    }

    private fun adapterRecyclerView() {
        binding.recyclerView.adapter = adapter
        viewModel.allTransaction.observe(requireActivity(), {
            it?.let {
                adapter.allTransaction = it
                balanceViewInit(it)
            }
        })
    }

    private fun button() {
        with(binding) {
            addTransaction.setOnClickListener {
                val action = DashboardFragmentDirections.actionDashboardFragmentToAddFragment(
                    Transaction(null, 0.0, "", "", "", "",""),
                    false
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun balanceViewInit(transactions: List<Transaction>) {
        var udhaarGiven = 0.0
        var udhaarTaken = 0.0
        transactions.forEach {
            if (it.transactionType == "Udhaar_taken") {
                udhaarGiven += it.amount
            } else {
                udhaarTaken += it.amount
            }
            binding.incomeCardView.givenTotal.text = udhaarGiven.toString()
            binding.incomeCardView.takenTotal.text = udhaarTaken.toString()
        }
    }

    private fun bottomNavOnClickListener() {
        binding.bottomAppBar.setOnMenuItemClickListener { item->
            when(item.itemId){
                R.id.logout -> {
                    auth.signOut()
                    Toast.makeText(requireContext(),"SignOut", Toast.LENGTH_LONG).show()
                    val intent= Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent).also{
                        activity?.finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
}