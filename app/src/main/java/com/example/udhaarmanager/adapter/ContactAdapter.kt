package com.example.udhaarmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.udhaarmanager.databinding.ContactLayoutBinding
import com.example.udhaarmanager.model.Contact

class ContactAdapter(
    private val listener: IContactAdapter
) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    var allContacts = listOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setData(contacts: List<Contact>) {
        allContacts = contacts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ContactLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        binding.contactCardView.setOnClickListener {
            listener.onItemClicked(allContacts[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = allContacts[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = allContacts.size

    inner class ViewHolder(private val binding: ContactLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contactDetail: Contact) {
            binding.contactName.text = contactDetail.name
            binding.contactNumber.text = contactDetail.number
            binding.contactImage.text = contactDetail.name?.subSequence(0..0)
        }
    }

    interface IContactAdapter {
        fun onItemClicked(contact: Contact)
    }
}