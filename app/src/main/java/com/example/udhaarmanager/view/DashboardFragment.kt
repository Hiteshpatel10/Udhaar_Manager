package com.example.udhaarmanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, TransactionViewModel>(),
    DashboardAdapter.IDashboardAdapter {
    override val viewModel: TransactionViewModel by viewModels()
    private lateinit var adapter: DashboardAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var allTransaction: ArrayList<ContactModel>
    private lateinit var all: ArrayList<FireStoreModel>

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
        allTransaction = arrayListOf()
        db.collection(auth.currentUser?.email.toString())
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.i("notes", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            Log.i("notes", "this")
                            try {
                                allTransaction.add(dc.document.toObject(ContactModel::class.java))
                            } catch (e: Exception) {
                                Log.i("notes", "error ${e.message}")
                            }
                        }
                    }
                    Log.i("notes", "$allTransaction")
                    adapter = DashboardAdapter(allTransaction, listener)
                    binding.recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    balanceViewInit(allTransaction)
                }
            })
    }

    private fun balanceViewInit(allTransaction: ArrayList<ContactModel>) {
        db = FirebaseFirestore.getInstance()
        db.collection(auth.currentUser?.email.toString())
            .document(allTransaction[0].number.toString())
            .collection(allTransaction[0].name.toString())
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.i("notes", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            Log.i("notes", "this")
                            try {
                                Log.i("notes2", "error")
                            } catch (e: Exception) {
                                Log.i("notes2", "error ${e.message}")
                            }
                        }
                    }
                    Log.i("notes", "$allTransaction")
                }
            })
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