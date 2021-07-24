package com.example.udhaarmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.udhaarmanager.R
import com.example.udhaarmanager.databinding.RecyclerviewLayoutBinding
import com.example.udhaarmanager.model.TModel

class TransactionAdapter(private var allTransaction: ArrayList<TModel>, private val listener: ITransactionListener) :
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
        fun bind(item: TModel) {
            if (item.transactionType == "Udhaar_taken") {
                binding.transactionIconView.setImageResource(R.drawable.income_symbol)
            } else {
                binding.transactionIconView.setImageResource(R.drawable.expense_symbol)
            }
            binding.transactionAmount.text = item.amount.toString()
            binding.transactionCategory.text = item.tag
            binding.transactionName.text = item.title
        }
    }

    interface ITransactionListener {
        fun onItemClicked(tModel: TModel)
    }
}
