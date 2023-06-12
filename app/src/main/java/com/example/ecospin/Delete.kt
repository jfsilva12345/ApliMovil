package com.example.ecospin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ecospin.databinding.DeleteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.ecospin.viewModel.DeleteVM

class Delete : AppCompatActivity() {

    private lateinit var binding: DeleteBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: DeleteVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        viewModel = ViewModelProvider(this).get(DeleteVM::class.java)

        binding.deleteAccountAppCompatButton.setOnClickListener {
            val password = binding.passwordEditText.text.toString()
            viewModel.deleteAccount(auth.currentUser, password)
        }

        viewModel.accountDeleted.observe(this) { isDeleted ->
            if (isDeleted) {
                Toast.makeText(this, "Se eliminó tu cuenta.", Toast.LENGTH_SHORT).show()
                signOut()
            } else {
                Toast.makeText(this, "La contraseña ingresada es incorrecta.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signOut() {
        auth.signOut()
        val intent = Intent(this, LogIn::class.java)
        startActivity(intent)
    }
}