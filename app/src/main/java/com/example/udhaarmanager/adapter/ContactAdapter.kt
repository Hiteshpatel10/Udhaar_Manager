package com.example.udhaarmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.udhaarmanager.databinding.ContactLayoutBinding
import com.example.udhaarmanager.model.ContactModel

class ContactAdapter(private val allContacts: List<ContactModel>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = allContacts[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = allContacts.size


    inner class ViewHolder(private val binding: ContactLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contactDetail: ContactModel){
            binding.text.text = contactDetail.number
        }
    }
}