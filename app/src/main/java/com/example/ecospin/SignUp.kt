package com.example.ecospin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ecospin.databinding.SignUpBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern
import com.example.ecospin.viewModel.SignUpVM


class SignUp : AppCompatActivity() {

    private lateinit var binding: SignUpBinding
    private lateinit var viewModel: SignUpVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SignUpVM::class.java)

        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val repeatPassword = binding.repeatPasswordEditText.text.toString()

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Ingrese un email válido.", Toast.LENGTH_SHORT).show()
            } else if (!viewModel.isValidPassword(password)) {
                Toast.makeText(this, "La contraseña es débil.", Toast.LENGTH_SHORT).show()
            } else if (password != repeatPassword) {
                Toast.makeText(this, "Confirma la contraseña.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.createAccount(email, password)
            }
        }

        binding.backImageView.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }

        viewModel.accountCreationResult.observe(this) { success ->
            if (success) {
                val intent = Intent(this, CkEmail::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this, "No se pudo crear la cuenta. Vuelva a intentarlo",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, CkEmail::class.java)
                startActivity(intent)
            }
        }
    }
}
