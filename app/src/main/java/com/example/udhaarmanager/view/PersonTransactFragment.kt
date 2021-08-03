package com.example.udhaarmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.udhaarmanager.R
import com.example.udhaarmanager.adapter.TransactionAdapter
import com.example.udhaarmanager.databinding.FragmentPersonTransactBinding
import com.example.udhaarmanager.model.FireStoreModel
import com.example.udhaarmanager.util.indianRupee
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class PersonTransactFragment : Fragment(), TransactionAdapter.ITransactionListener {

    private lateinit var binding: FragmentPersonTransactBinding
    private val args: PersonTransactFragmentArgs by navArgs()
    private var auth = Firebase.auth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: TransactionAdapter
    private lateinit var allTransaction: ArrayList<FireStoreModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPersonTransactBinding.inflate(inflater, container, false)

        adapterRecyclerView(this)
        onScrolled()
        bottomNavOnClickListener()
        return binding.root
    }


    private fun adapterRecyclerView(listener: TransactionAdapter.ITransactionListener) {
        db = FirebaseFirestore.getInstance()
        allTransaction = arrayListOf()
        db.collection(auth.currentUser?.email.toString())
            .document(args.transactor.number.toString()).collection(args.transactor.name.toString())
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null)
                        return

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            Timber.i("added transaction")
                            allTransaction.add(dc.document.toObject(FireStoreModel::class.java))
                        }
                    }
                    adapter = TransactionAdapter(listener, allTransaction)
                    binding.recyclerView.adapter = adapter
                    balanceViewInit(allTransaction)
                    adapter.notifyDataSetChanged()
                }
            })
    }

    private fun onScrolled() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.addTransaction.shrink()
                } else {
                    binding.addTransaction.extend()
                }

                if ((dy > (recyclerView.layoutManager?.itemCount!!)) && (recyclerView.layoutManager?.itemCount!! > 4)) {
                    binding.addTransaction.hide()
                } else {
                    binding.addTransaction.show()
                }
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

    private fun balanceViewInit(transactions: List<FireStoreModel>) {
        var udhaarGiven = 0.0
        var udhaarTaken = 0.0

        transactions.forEach {

            if (it.transactionType == "Udhaar_taken")
                udhaarGiven += it.amount!!
            else
                udhaarTaken += it.amount!!

            if ((udhaarGiven - udhaarTaken) < 0) {
                binding.balanceView.udhaarText.setText(R.string.text_youWillGet)
                binding.balanceView.udhaarAmount.text = indianRupee(udhaarTaken - udhaarGiven)
            } else {
                binding.balanceView.udhaarText.setText(R.string.text_youWillGive)
                binding.balanceView.udhaarAmount.text = indianRupee(udhaarGiven - udhaarTaken)
            }
        }
    }

    override fun onItemClicked(transaction: FireStoreModel) {
        val action = PersonTransactFragmentDirections.actionPersonTransactFragmentToDetailFragment(
            transaction,
            args.transactor
        )
        findNavController().navigate(action)
    }
}