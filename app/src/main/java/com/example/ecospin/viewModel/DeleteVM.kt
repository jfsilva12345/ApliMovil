package com.example.ecospin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DeleteVM : ViewModel() {
    private val _accountDeleted = MutableLiveData<Boolean>()
    val accountDeleted: LiveData<Boolean> get() = _accountDeleted

    fun deleteAccount(user: FirebaseUser?, password: String) {
        if (user != null) {
            val email = user.email
            val credential = EmailAuthProvider.getCredential(email!!, password)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.delete()
                            .addOnCompleteListener { taskDeleteAccount ->
                                _accountDeleted.value = taskDeleteAccount.isSuccessful
                            }
                    } else {
                        _accountDeleted.value = false
                    }
                }
        } else {
            _accountDeleted.value = false
        }
    }
}