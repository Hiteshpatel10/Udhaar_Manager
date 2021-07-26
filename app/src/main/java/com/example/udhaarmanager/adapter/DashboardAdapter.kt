package com.example.udhaarmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.udhaarmanager.databinding.TransactLayoutBinding
import com.example.udhaarmanager.model.ContactModel

class DashboardAdapter(
    private val allTransact: ArrayList<ContactModel>,
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
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = allTransact[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = allTransact.size

    inner class ViewHolder(val binding: TransactLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactModel) {
            binding.transactName.text = item.name
        }
    }

    interface IDashboardAdapter {
        fun onItemClicked(transactor: ContactModel)
    }
}