package com.example.udhaarmanager.util

import android.widget.Toast

object Constants {

    val transactionType = listOf(
        "Udhaar_taken",
        "Udhaar_given"
    )

    val transactionTags = listOf(
        "Housing",
        "Transportation",
        "Food",
        "Utilities",
        "Insurance",
        "Healthcare",
        "Saving & Debts",
        "Personal Spending",
        "Entertainment",
        "Miscellaneous"
    )
}

//when {
//    transaction.title.isNullOrEmpty() -> {
//        Toast.makeText(requireContext(), "fh", Toast.LENGTH_LONG).show()
//    }
//
//    transaction.amount.isNaN() -> {
//        Toast.makeText(requireContext(), "fh", Toast.LENGTH_LONG).show()
//    }
//
//    transaction.transactionType.isEmpty() -> {
//        Toast.makeText(requireContext(), "fh", Toast.LENGTH_LONG).show()
//    }
//
//    transaction.tag.isNullOrEmpty() -> {
//        Toast.makeText(requireContext(), "fh", Toast.LENGTH_LONG).show()
//    }
//
//    transaction.borrowDate.isEmpty() -> {
//        Toast.makeText(requireContext(), "fh", Toast.LENGTH_LONG).show()
//    }
//
//    transaction.returnDate.isEmpty() -> {
//        Toast.makeText(requireContext(), "fh", Toast.LENGTH_LONG).show()
//    }
//
//    transaction.note.isEmpty() -> {
//        Toast.makeText(requireContext(), "fh", Toast.LENGTH_LONG).show()
//    }