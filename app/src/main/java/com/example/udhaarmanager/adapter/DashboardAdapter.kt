package com.example.udhaarmanager.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.udhaarmanager.databinding.TransactLayoutBinding
import com.example.udhaarmanager.model.ContactModel
import com.example.udhaarmanager.model.FireStoreModel
import com.example.udhaarmanager.util.indianRupee

class DashboardAdapter(
    private val allTransact: ArrayList<ContactModel>,
    private val allTransactions: ArrayList<FireStoreModel>,
    private val listener: IDashboardAdapter
) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TransactLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        binding.transactCardView.setOnClickListener {
            listener.onItemClicked(allTransact[viewHolder.adapterPosition])
        }

        binding.deleteTransactor.setOnClickListener {
            listener.onLongPressed(allTransact[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTransact = allTransact[position]
        holder.bind(itemTransact)
    }

    override fun getItemCount(): Int = allTransact.size

    inner class ViewHolder(val binding: TransactLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var udhaarGiven = 0.0
        var udhaarTaken = 0.0
        fun bind(itemTransact: ContactModel) {
            binding.transactName.text = itemTransact.name
            binding.contactImage.text = itemTransact.name!!.subSequence(0..0)
            allTransactions.forEach {
                if (itemTransact.number == it.number) {
                    if (it.transactionType == "Udhaar_taken") {
                        udhaarGiven += it.amount!!
                    } else {
                        udhaarTaken += it.amount!!
                    }
                    binding.transactLast.text = it.createdAtDateFormat
                }
                binding.transactTotal.text = indianRupee(udhaarGiven - udhaarTaken)
                if (udhaarGiven - udhaarTaken < 0.0) {
                    binding.transactTotal.setTextColor(Color.parseColor("#e50000"))
                } else {
                    binding.transactTotal.setTextColor(Color.parseColor("#007300"))
                }

            }
        }
    }

    interface IDashboardAdapter {
        fun onItemClicked(transactor: ContactModel)
        fun onLongPressed(transactor: ContactModel)
    }
}

