package com.example.udhaarmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.udhaarmanager.databinding.RecyclerviewLayoutBinding
import com.example.udhaarmanager.model.Transaction

class TransactionAdapter(private val listener: ITransactionListener) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    var allTransaction = listOf<Transaction>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            RecyclerviewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = TransactionViewHolder(binding)

        binding.cardView.setOnClickListener{
            listener.onItemClicked(allTransaction[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = allTransaction[position]
        holder.binding.text1.text = item.tag
    }

    override fun getItemCount(): Int {
        return allTransaction.size
    }

    inner class TransactionViewHolder(val binding: RecyclerviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ITransactionListener {
        fun onItemClicked(transaction: Transaction)
    }

}