package com.example.lingvomate.presentation.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import com.example.lingvomate.R
import com.example.lingvomate.presentation.screen.models.RegisterDataError
import com.example.lingvomate.presentation.screen.state.RegisterScreenEvent
import com.example.lingvomate.presentation.screen.state.RegisterScreenState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID

class RegisterViewModel() : ViewModel() {

    var state by mutableStateOf(RegisterScreenState())
        private set
    var registerDataError = mutableStateOf(RegisterDataError())
        private set
    var isFormValid by mutableStateOf(false)
        private set

    fun onEvent(event: RegisterScreenEvent) {
        when (event) {
            is RegisterScreenEvent.EmailUpdated -> this.state = state.copy(email = event.newEmail)
            is RegisterScreenEvent.PasswordUpdated -> this.state =
                state.copy(password = event.newPassword)
            is RegisterScreenEvent.ConfirmPasswordUpdated -> this.state =
                state.copy(confirmPassword = event.newConfirmPassword)
            is RegisterScreenEvent.CreateUser -> signUp(auth = event.auth,)
            is RegisterScreenEvent.ValidateEmail -> validateEmail()
            is RegisterScreenEvent.ValidatePassword -> validatePassword()
            is RegisterScreenEvent.DataValidate -> updateFormValidity()
        }
    }


    fun validateEmail() {
        val newErrorMessage = when {
            state.email.isBlank() -> "Email не может быть пустым"
            !Patterns.EMAIL_ADDRESS.matcher(state.email).matches() -> "Введите корректный email"
            else -> ""
        }
        this.registerDataError.value = registerDataError.value.copy(emailError = newErrorMessage)
        updateFormValidity()
    }

    fun validatePassword() {
        val newErrorMessage = when {
            state.password.isBlank() -> "Пароль не может быть пустым"
            state.password.length < 6 -> "Пароль должен содержать хотя бы 6 символов"
            !state.password.any { it.isUpperCase() } -> "Пароль должен содержать хотя бы одну заглавную букву"
            !state.password.any { it.isDigit() } -> "Пароль должен содержать хотя бы одну цифру"
            else -> ""
        }
        this.registerDataError.value = registerDataError.value.copy(passwordError = newErrorMessage)
        updateFormValidity()
    }

    private fun updateFormValidity(): Boolean {
        isFormValid = registerDataError.value.emailError.isEmpty() && registerDataError.value.passwordError.isEmpty()
        return isFormValid
    }





    fun signUp(auth: FirebaseAuth) {
        if (state.email.isNotEmpty() && state.password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(state.email, state.password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                sendVerificationEmail()
                            }else {

                            }
                        }
                }catch (e : Exception) {
                    withContext(Dispatchers.Main) {

                    }
                }
            }
        }

    }


    fun sendVerificationEmail() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user?.sendEmailVerification()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Register", "The letter with confirmation sent")
                } else {
                    Log.e("Register", "Error ")
                }
            }
    }




}