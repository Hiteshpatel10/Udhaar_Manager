package com.example.udhaarmanager.view


import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.udhaarmanager.R
import com.example.udhaarmanager.adapter.ContactAdapter
import com.example.udhaarmanager.databinding.FragmentAddContactBinding
import com.example.udhaarmanager.model.ContactModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AddContactFragment : Fragment(), ContactAdapter.IContactAdapter{

    private var contactList: MutableList<ContactModel> = ArrayList()
    private lateinit var adapter: ContactAdapter
    private lateinit var binding: FragmentAddContactBinding

    private var contactsColumn = listOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID,
    ).toTypedArray()

    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionRef = auth.currentUser?.email.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)

        readContacts()

        adapter = ContactAdapter(contactList, this)
        binding.contactRecyclerView.adapter = adapter

        return binding.root
    }

    //RecyclerView Interface Implemented
    override fun onItemClicked(contact: ContactModel) {
        contact.number?.let {
            db.collection(collectionRef).document(it).set(contact).also {
                findNavController().navigate(R.id.action_addPersonFragment_to_dashboardFragment)
            }
        }
    }

    // Reading Contacts Using Content Provider
    private fun readContacts() {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val contacts = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            contactsColumn,
            ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        while (contacts?.moveToNext()!!) {
            val name: String =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            contactList.add(ContactModel(name, number))
        }
        contacts.close()
    }

}