package com.example.foodies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.foodies.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sign

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.editTextTextPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        flag = true
                        val intent = Intent(this, MenusListActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this,"Lo sentimos, no pudimos encontrar tu cuenta.", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Porfavor llene todos los campos.", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()

        /* val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
        } */

       if(firebaseAuth.currentUser != null && flag){
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
        }

    }
}