package com.example.udhaarmanager.view


import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.udhaarmanager.R
import com.example.udhaarmanager.adapter.ContactAdapter
import com.example.udhaarmanager.model.ContactModel
import kotlinx.android.synthetic.main.fragment_add_person.view.*

class AddPersonFragment : Fragment() {
    private var contactList: MutableList<ContactModel> = ArrayList()
    private lateinit var adapter: ContactAdapter
    private var contactsColumn = listOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID,
    ).toTypedArray()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_add_person, container, false)
        readContacts()
        adapter = ContactAdapter(contactList)
        binding.contact_recyclerView.adapter = adapter
        return binding
    }

    private fun readContacts() {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val contacts = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            contactsColumn,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        while (contacts?.moveToNext()!!) {
            val name =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            contactList.add(ContactModel(name, number))
        }
        Log.i("notes", "$contactList")
        contacts.close()
    }
}