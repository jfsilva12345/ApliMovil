package com.example.ecospin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UpdPassVM : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _passwordChangeResult = MutableLiveData<Boolean>()
    val passwordChangeResult: LiveData<Boolean> = _passwordChangeResult

    fun changePassword(currentPassword: String, newPassword: String) {
        val user = auth.currentUser

        if (user != null) {
            val email = user.email
            val credential = EmailAuthProvider.getCredential(email!!, currentPassword)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { taskUpdatePassword ->
                                _passwordChangeResult.value = taskUpdatePassword.isSuccessful
                            }
                    } else {
                        _passwordChangeResult.value = false
                    }
                }
        }
    }
}
