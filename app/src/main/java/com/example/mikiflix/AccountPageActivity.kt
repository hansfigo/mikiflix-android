package com.example.mikiflix

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mikiflix.databinding.AccountPageActivityBinding
import com.example.mikiflix.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class AccountPageActivity : Activity() {
    private lateinit var binding: AccountPageActivityBinding
    private lateinit var bindingBottomSheet: BottomSheetLayoutBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Render Seluruh UI
        binding = AccountPageActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Button
        val btnLogout = binding.btnLogout
        val btnEdit = binding.edit
        val btnDeleteAccount = binding.btnDeleteAcc


        // Button Listener, lakukan something ketika ada interaksi pada button tadi


        //Listener untuk Edit Username dan summon bottom sheet
        btnEdit.setOnClickListener {
            // Create a bottom sheet dialog
            val bottomSheetDialog = BottomSheetDialog(this)
            bindingBottomSheet = BottomSheetLayoutBinding.inflate(layoutInflater)
            bottomSheetDialog.setContentView(bindingBottomSheet.root)

            // Show the bottom sheet dialog
            bottomSheetDialog.show()

            // Set the text after the bottom sheet dialog is shown
            updateEditTextFormText()

            val buttonSaveUsername = bindingBottomSheet.buttonEditName

            //Update username dan close bottom Sheet
            buttonSaveUsername.setOnClickListener {
                updateUsername()
                bottomSheetDialog.dismiss()
            }
        }

        //Listener untuk Logout
        btnLogout.setOnClickListener {
            signOut()
        }
        btnDeleteAccount.setOnClickListener {
            deleteAccount()
        }
    }

    override fun onStart() {
        super.onStart()

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.userEmail.text = auth.currentUser?.email.toString()

        currentUser?.let {
            val name = it.displayName
            if (!name.isNullOrEmpty()) {
                binding.userName.text = name
                binding.userName.visibility = View.VISIBLE

                // Update the text in the bottom sheet if it's already shown
                updateEditTextFormText()
            } else {
                binding.noname.visibility = View.VISIBLE
            }
        }
    }


    private fun updateUsername() {
        // Check if bindingBottomSheet is initialized and editTextForm is not null
        if (::bindingBottomSheet.isInitialized && bindingBottomSheet.editTextForm != null) {
            val newName = bindingBottomSheet.editTextForm.text.toString()

            val profileUpdates = userProfileChangeRequest {
                displayName = newName
            }

            auth.currentUser!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile updated.")
                    }
                }
            binding.userName.text = newName

        }
    }



    private fun updateEditTextFormText() {
        // Check if bindingBottomSheet is initialized and editTextForm is not null
        if (::bindingBottomSheet.isInitialized && bindingBottomSheet.editTextForm != null) {
            val name = auth.currentUser?.displayName
            name?.let {
                bindingBottomSheet.editTextForm.setText(it)
            }
        }
    }

    private fun deleteAccount(){
        auth.currentUser?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
    }


    private fun signOut() {
        auth.signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
