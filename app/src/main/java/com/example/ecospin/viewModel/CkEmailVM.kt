package com.example.ecospin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest

class CkEmailVM : ViewModel() {
    private val _emailVerificationResult = MutableLiveData<Boolean>()
    val emailVerificationResult: LiveData<Boolean> get() = _emailVerificationResult

    fun updateProfile(user: FirebaseUser?) {
        val profileUpdates = userProfileChangeRequest {}
        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _emailVerificationResult.value = user.isEmailVerified
                }
            }
    }
}