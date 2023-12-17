package com.example.mikiflix

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mikiflix.databinding.AccountPageActivityBinding
import com.example.mikiflix.databinding.HomePageBinding
import com.google.common.base.Strings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountPageActivity : Activity() {
    private lateinit var binding: AccountPageActivityBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AccountPageActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogout.setOnClickListener {
            signOut()
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
            } else {
                binding.noname.visibility = View.VISIBLE
            }
        }
    }

    private fun signOut (){
        auth.signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}