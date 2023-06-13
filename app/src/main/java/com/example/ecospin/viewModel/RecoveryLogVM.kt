package com.example.ecospin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoveryLogVM : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _passwordResetResult = MutableLiveData<Boolean>()
    val passwordResetResult: LiveData<Boolean> = _passwordResetResult

    fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                _passwordResetResult.value = task.isSuccessful
            }
    }
}