package com.example.ecospin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ecospin.databinding.RecoveryLogBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.ecospin.viewModel.RecoveryLogVM

class RecoveryLog : AppCompatActivity() {

    private lateinit var binding: RecoveryLogBinding
    private lateinit var viewModel: RecoveryLogVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecoveryLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RecoveryLogVM::class.java)

        binding.senEmailAppCompatButton.setOnClickListener {
            val emailAddress = binding.emailEditText.text.toString()
            viewModel.sendPasswordResetEmail(emailAddress)
        }

        viewModel.passwordResetResult.observe(this) { success ->
            if (success) {
                val intent = Intent(this, LogIn::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(baseContext, "Ingrese un email de una cuenta válida.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}