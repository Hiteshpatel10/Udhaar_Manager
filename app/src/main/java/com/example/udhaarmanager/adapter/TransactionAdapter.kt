package com.example.udhaarmanager.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.udhaarmanager.R
import com.example.udhaarmanager.databinding.RecyclerviewLayoutBinding
import com.example.udhaarmanager.model.FireStoreModel
import com.example.udhaarmanager.util.indianRupee

class TransactionAdapter(
    private val listener: ITransactionListener,
    private val allTransaction: ArrayList<FireStoreModel>
) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            RecyclerviewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = TransactionViewHolder(binding)

        binding.transactionLayout.setOnClickListener {
            listener.onItemClicked(allTransaction[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = allTransaction[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return allTransaction.size
    }

    inner class TransactionViewHolder(private val binding: RecyclerviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FireStoreModel) {
            if (item.transactionType == "Udhaar_taken") {
                binding.givenLayout.transactionCardView.isVisible = false
                binding.takenLayout.transactionIconView.setImageResource(R.drawable.income_symbol)
                binding.takenLayout.transactionAmount.setTextColor(Color.parseColor("#e50000"))
                binding.takenLayout.transactionAmount.text = indianRupee(item.amount!!)
                binding.takenLayout.transactionCategory.text = item.tag
                binding.takenLayout.transactionName.text = item.title
            } else {
                binding.takenLayout.transactionCardView.isVisible = false
                binding.givenLayout.transactionIconView.setImageResource(R.drawable.expense_symbol)
                binding.givenLayout.transactionAmount.setTextColor(Color.parseColor("#007300"))
                binding.givenLayout.transactionAmount.text = indianRupee(item.amount!!)
                binding.givenLayout.transactionCategory.text = item.tag
                binding.givenLayout.transactionName.text = item.title
            }
        }
    }

    interface ITransactionListener {
        fun onItemClicked(transaction: FireStoreModel)
    }

}