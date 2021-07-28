package com.example.udhaarmanager.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.udhaarmanager.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var layout: View
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                val snack =
                    Snackbar.make(layout, "Permission Required", Snackbar.LENGTH_LONG)
                        .setAction("Grant Permission") {
                            onClickRequestPermission()
                        }
                snack.show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.mainActivity)
        onClickRequestPermission()
    }

    private fun onClickRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS,
            ) == PackageManager.PERMISSION_GRANTED -> {

            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS
            ) -> {
                Snackbar.make(
                    layout,
                    "Permission Required To Add Transactor",
                    Snackbar.LENGTH_LONG
                ).show()
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS
                )
            }
            else -> {
                Snackbar.make(
                    layout,
                    "Contact Read Permission Not Given",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }
}