package com.example.udhaarmanager.view


import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.udhaarmanager.R
import com.example.udhaarmanager.adapter.ContactAdapter
import com.example.udhaarmanager.databinding.FragmentAddContactBinding
import com.example.udhaarmanager.model.ContactModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AddContactFragment : Fragment(), ContactAdapter.IContactAdapter {

    private var contactList: MutableList<ContactModel> = ArrayList()
    private lateinit var adapter: ContactAdapter
    private lateinit var binding: FragmentAddContactBinding
    private lateinit var layout: View

    private var contactsColumn = listOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID,
    ).toTypedArray()

    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionRef = auth.currentUser?.email.toString()


    //Request Contact Permission
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                val snack =
                    Snackbar.make(layout, "Permission Required", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Grant Permission") {
                            onClickRequestPermission()
                        }
                snack.show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)
        layout = binding.addContactFragment

        onClickRequestPermission()

        //RecyclerView Adapter Initialized
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

    //Requesting Contact Read Permission At Runtimen
    private fun onClickRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS,
            ) == PackageManager.PERMISSION_GRANTED -> {
                readContacts()
                Toast.makeText(requireContext(), "ag", Toast.LENGTH_LONG).show()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_CONTACTS
            ) -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS
                )
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS
                )
            }

        }
    }
}

