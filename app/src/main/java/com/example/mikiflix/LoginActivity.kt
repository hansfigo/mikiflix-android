package com.example.mikiflix

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mikiflix.databinding.ActivityMainBinding
import com.example.mikiflix.databinding.HomePageBinding
import com.example.mikiflix.databinding.LoginPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : Activity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LoginPageBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginPageBinding.inflate(layoutInflater)

        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }


        val view = binding.root
        setContentView(view)


        binding.btnLogin.setOnClickListener {

            val email = binding.inputEmail.text.toString()
            val password = binding.inputPasswd.text.toString()

            signIn(email, password)
        }

        binding.navRegister.setOnClickListener {
            val intent = Intent(this, RegisterPageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email : String,  password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                                }
            }
    }
}