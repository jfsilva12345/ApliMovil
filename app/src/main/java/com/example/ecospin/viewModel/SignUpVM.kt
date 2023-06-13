package com.example.ecospin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUpVM : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _accountCreationResult = MutableLiveData<Boolean>()
    val accountCreationResult: LiveData<Boolean> = _accountCreationResult

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _accountCreationResult.value = task.isSuccessful
            }
    }

    fun isValidPassword(password: String): Boolean {
        val passwordRegex = Pattern.compile("^(?=.*[-@#$%^&+=]).{6,}$")
        return passwordRegex.matcher(password).matches()
    }
}