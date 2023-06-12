package com.example.ecospin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ecospin.databinding.UpdPassBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern
import com.example.ecospin.viewModel.UpdPassVM

class UpdPass : AppCompatActivity() {

    private lateinit var binding: UpdPassBinding
    private lateinit var viewModel: UpdPassVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UpdPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(UpdPassVM::class.java)

        val passwordRegex = Pattern.compile("^" +
                "(?=.*[-@#$%^&+=])" +     // Al menos 1 carácter especial
                ".{6,}" +                // Al menos 4 caracteres
                "$")

        binding.changePasswordAppCompatButton.setOnClickListener {
            val currentPassword = binding.currentPasswordEditText.text.toString()
            val newPassword = binding.newPasswordEditText.text.toString()
            val repeatPassword = binding.repeatPasswordEditText.text.toString()

            if (newPassword.isEmpty() || !passwordRegex.matcher(newPassword).matches()) {
                Toast.makeText(this, "La contraseña es débil.", Toast.LENGTH_SHORT).show()
            } else if (newPassword != repeatPassword) {
                Toast.makeText(this, "Confirma la contraseña.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.changePassword(currentPassword, newPassword)
            }
        }

        viewModel.passwordChangeResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Se cambió la contraseña.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this, "La contraseña actual es incorrecta.", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}