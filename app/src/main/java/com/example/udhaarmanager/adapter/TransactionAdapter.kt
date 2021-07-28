package com.example.udhaarmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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

        binding.transactionCardView.setOnClickListener {
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
                binding.transactionIconView.setImageResource(R.drawable.income_symbol)
            } else {
                binding.transactionIconView.setImageResource(R.drawable.expense_symbol)
            }
            binding.transactionAmount.text = indianRupee(item.amount!!)
            binding.transactionCategory.text = item.tag
            binding.transactionName.text = item.title
        }
    }

    interface ITransactionListener {
        fun onItemClicked(transaction: FireStoreModel)
    }

}