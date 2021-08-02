package com.example.udhaarmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.udhaarmanager.R
import com.example.udhaarmanager.base.BaseFragment
import com.example.udhaarmanager.databinding.FragmentDetailBinding
import com.example.udhaarmanager.main.viewmodel.TransactionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, TransactionViewModel>() {
    override val viewModel: TransactionViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionRef = auth.currentUser?.email.toString()

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
        detailLayout.titleValue.text = args.transaction.title
        detailLayout.amountValue.text = args.transaction.amount.toString()
        detailLayout.transactionTypeValue.text = args.transaction.transactionType
        detailLayout.tagValue.text = args.transaction.tag
        detailLayout.borrowDateValue.text = args.transaction.borrowDate
        detailLayout.returnDateValue.text = args.transaction.returnDate
        detailLayout.noteValue.text = args.transaction.note
        detailLayout.createdAtValue.text = args.transaction.createdAtDateFormat
    }

    private fun shareAsText() {
        val share = getString(
            R.string.share_message,
            args.transaction.title.toString(),
            args.transaction.amount.toString(),
            args.transaction.transactionType.toString(),
            args.transaction.tag.toString(),
            args.transaction.borrowDate.toString(),
            args.transaction.note.toString(),
            args.transaction.createdAtDateFormat.toString()
        )

        val intent = ShareCompat.IntentBuilder(requireActivity())
            .setType("text/plain")
            .setText(share)
            .intent
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    private fun bottomNavOnClickListeners() {
        binding.bottomAppBar.setNavigationOnClickListener {
            val action =
                DetailFragmentDirections.actionDetailFragmentToPersonTransactFragment(
                    args.transactor
                )
            findNavController().navigate(action)
        }

        binding.bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.deleteButton -> {
                    db.collection(collectionRef).document(args.transactor.number.toString())
                        .collection(args.transactor.name.toString())
                        .document(args.transaction.createdAt.toString())
                        .delete().addOnSuccessListener {
                            val action =
                                DetailFragmentDirections.actionDetailFragmentToPersonTransactFragment(
                                    args.transactor
                                )
                            findNavController().navigate(action)
                        }
                    true
                }

                R.id.shareButton -> {
                    shareAsText()
                    true
                }

                R.id.updateButton -> {
                    val action = DetailFragmentDirections.actionDetailFragmentToAddFragment(
                        args.transaction,
                        true,
                        args.transactor
                    )
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }
    }
}