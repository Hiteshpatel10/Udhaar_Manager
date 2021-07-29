package com.example.udhaarmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.udhaarmanager.R
import com.example.udhaarmanager.adapter.DashboardAdapter
import com.example.udhaarmanager.auth.AuthActivity
import com.example.udhaarmanager.databinding.FragmentDashboardBinding
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
class DashboardFragment : Fragment(),
    DashboardAdapter.IDashboardAdapter {
    private lateinit var adapter: DashboardAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var allTransactor: ArrayList<ContactModel>
    private lateinit var allTransaction: ArrayList<FireStoreModel>
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)

        auth = Firebase.auth
        addContact()
        adapterRecyclerView(this)
        bottomNavOnClickListener()

        NavigationUI.setupWithNavController(binding.navView, findNavController())
        return binding.root
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
                    balanceDataGather(allTransactor, listener)
                }
            })
    }

    private fun balanceDataGather(
        allTransactor: ArrayList<ContactModel>,
        listener: DashboardAdapter.IDashboardAdapter
    ) {
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
                        balanceViewInit(allTransaction, listener)
                    }
                })
        }

    }

    private fun balanceViewInit(
        transactions: ArrayList<FireStoreModel>,
        listener: DashboardAdapter.IDashboardAdapter
    ) {
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
        adapter = DashboardAdapter(allTransactor, allTransaction, listener)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun bottomNavOnClickListener() {

        //Navigation Drawer Open
        binding.bottomAppBar.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navView)
        }
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