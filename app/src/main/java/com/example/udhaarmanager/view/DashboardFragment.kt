package com.example.udhaarmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.udhaarmanager.R
import com.example.udhaarmanager.adapter.DashboardAdapter
import com.example.udhaarmanager.auth.AuthActivity
import com.example.udhaarmanager.base.BaseFragment
import com.example.udhaarmanager.databinding.FragmentDashboardBinding
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel
import com.example.udhaarmanager.model.ContactModel
import com.example.udhaarmanager.model.FireStoreModel
import com.example.udhaarmanager.util.indianRupee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, TransactionViewModel>(),
    DashboardAdapter.IDashboardAdapter {
    override val viewModel: TransactionViewModel by viewModels()
    private lateinit var adapter: DashboardAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var allTransactor: ArrayList<ContactModel>
    private lateinit var allTransaction: ArrayList<FireStoreModel>

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        addContact()
        adapterRecyclerView(this)
        bottomNavOnClickListener()
    }

    override fun onItemClicked(transactor: ContactModel) {
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToPersonTransactFragment(transactor)
        findNavController().navigate(action)

    }

    private fun addContact() {
        binding.addTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addPersonFragment)
        }
    }

    private fun adapterRecyclerView(listener: DashboardAdapter.IDashboardAdapter) {
        db = FirebaseFirestore.getInstance()
        allTransactor = arrayListOf()
        db.collection(auth.currentUser?.email.toString())
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Timber.e(error)
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            try {
                                allTransactor.add(dc.document.toObject(ContactModel::class.java))
                            } catch (e: Exception) {
                                Timber.e(e)
                            }
                        }
                    }
                    adapter = DashboardAdapter(allTransactor, allTransaction,listener)
                    binding.recyclerView.adapter = adapter
                    balanceDataGather(allTransactor)
                    adapter.notifyDataSetChanged()
                }
            })
    }

    private fun balanceDataGather(allTransactor: ArrayList<ContactModel>) {
        allTransaction = arrayListOf()
        allTransactor.forEach {
            db.collection(auth.currentUser?.email.toString())
                .document(it.number.toString())
                .collection(it.name.toString())
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            Timber.e(error)
                            return
                        }
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                try {
                                    allTransaction.add(dc.document.toObject(FireStoreModel::class.java))
                                } catch (e: Exception) {
                                    Timber.e(e)
                                }
                            }
                        }
                        balanceViewInit(allTransaction)
                    }
                })
        }

    }

    private fun balanceViewInit(transactions: ArrayList<FireStoreModel>) {
        var udhaarGiven = 0.0
        var udhaarTaken = 0.0
        transactions.forEach {
            if (it.transactionType == "Udhaar_taken") {
                udhaarGiven += it.amount!!
            } else {
                udhaarTaken += it.amount!!
            }
            binding.incomeCardView.givenTotal.text = indianRupee(udhaarGiven)
            binding.incomeCardView.takenTotal.text = indianRupee(udhaarTaken)
        }
    }

    private fun bottomNavOnClickListener() {
        binding.bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    auth.signOut()
                    Toast.makeText(requireContext(), "SignOut", Toast.LENGTH_LONG).show()
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent).also {
                        activity?.finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
}