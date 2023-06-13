package com.example.ecospin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ecospin.databinding.LogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.ecospin.viewModel.LogInVM

class LogIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LogInBinding
    private lateinit var viewModel: LogInVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        viewModel = ViewModelProvider(this).get(LogInVM::class.java)

        binding.signInAppCompatButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.signIn(email, password)
        }

        binding.signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.recoveryAccountTextView.setOnClickListener {
            val intent = Intent(this, RecoveryLog::class.java)
            startActivity(intent)
        }

        viewModel.signInResult.observe(this) { signInSuccessful  ->
            if (signInSuccessful) {
                Log.d("TAG", "signInWithEmail:success")
                reload()
            } else {
                Log.w("TAG", "signInWithEmail:failure")
                Toast.makeText(baseContext, "Email o contraseña incorrectos.",
                    Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.currentUser.observe(this) { currentUser ->
            if (currentUser != null) {
                if (currentUser.isEmailVerified) {
                    reload()
                } else {
                    val intent = Intent(this, CkEmail::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkCurrentUser()
    }

    private fun reload() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}