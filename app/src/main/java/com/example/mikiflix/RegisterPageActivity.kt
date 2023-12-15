package com.example.mikiflix

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mikiflix.databinding.LoginPageBinding
import com.example.mikiflix.databinding.RegisterPageActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterPageActivity : Activity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterPageActivityBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterPageActivityBinding.inflate(layoutInflater)

        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()

        // Check sudah login atau belum , kalo udah di redirect ke home page
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

        // Render Tampilan
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPasswd.text.toString()
            register(email, password)
        }

        binding.navLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register(email : String,  password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}