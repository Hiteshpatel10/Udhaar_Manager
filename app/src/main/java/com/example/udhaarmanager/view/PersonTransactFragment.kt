package com.example.udhaarmanager.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.udhaarmanager.adapter.TransactionAdapter
import com.example.udhaarmanager.databinding.FragmentPersonTransactBinding
import com.example.udhaarmanager.model.FireStoreModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

class PersonTransactFragment : Fragment(), TransactionAdapter.ITransactionListener {

    private lateinit var binding: FragmentPersonTransactBinding
    private val args: PersonTransactFragmentArgs by navArgs()
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: TransactionAdapter
    private lateinit var allTransaction: ArrayList<FireStoreModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPersonTransactBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        adapterRecyclerView(this)
        bottomNavOnClickListener()
        return binding.root
    }


    override fun onItemClicked(transaction: FireStoreModel) {
        TODO("Not yet implemented")
    }

    private fun adapterRecyclerView(listener: TransactionAdapter.ITransactionListener) {
        db = FirebaseFirestore.getInstance()
        allTransaction = arrayListOf()
        db.collection(auth.currentUser?.email.toString()).document(args.transactor.number.toString())
            .collection(args.transactor.name.toString())
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
                                allTransaction.add(dc.document.toObject(FireStoreModel::class.java))
                            } catch (e: Exception) {
                                Log.i("notes", "error ${e.message}")
                            }
                        }
                    }
                    Log.i("notes", "$allTransaction")
                    adapter = TransactionAdapter(listener, allTransaction)
                    binding.recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            })
    }

    private fun bottomNavOnClickListener() {
        binding.addTransaction.setOnClickListener {
            val action = PersonTransactFragmentDirections.actionPersonTransactFragmentToAddFragment(
                FireStoreModel(null, null, null, null, null, null, null, null),
                false,
                args.transactor
            )
            findNavController().navigate(action)
        }
    }
}