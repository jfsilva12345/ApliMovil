
package com.example.ecospin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult

class LogInVM : ViewModel() {
    private val _signInResult = MutableLiveData<Boolean>()
    val signInResult: LiveData<Boolean> get() = _signInResult

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> get() = _currentUser

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _signInResult.value = task.isSuccessful
            }
    }

    fun checkCurrentUser() {
        val currentUser = auth.currentUser
        _currentUser.value = currentUser
    }
}
