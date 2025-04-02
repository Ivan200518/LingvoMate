package com.example.lingvomate.presentation.viewmodel

import GoogleAuthClient
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lingvomate.presentation.navigation.Screen
import com.example.lingvomate.presentation.screen.state.LoginScreenEvent
import com.example.lingvomate.presentation.screen.state.LoginScreenState
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val googleAuthClient: GoogleAuthClient,
) : ViewModel() {
    var state by mutableStateOf(LoginScreenState())
    var loginMessage by mutableStateOf("")
    private val auth = FirebaseAuth.getInstance()

    fun onEvent(event : LoginScreenEvent)  {
        when(event) {
            is LoginScreenEvent.EmailUpdated -> this.state = state.copy(email = event.newEmail)
            is LoginScreenEvent.PasswordUpdated -> this.state = state.copy(password = event.newPassword)
            is LoginScreenEvent.SignIn -> loginWithEmail(state.email,state.password,onSuccess = event.onSuccess)
            is LoginScreenEvent.GoogleSignIn ->viewModelScope.launch {
                googleAuthClient.signIn()
                if (googleAuthClient.isSingedIn()) {
                    event.onSuccess(Screen.Home)
                }

            }

        }
    }


    fun loginWithEmail(email: String, password: String, onSuccess: (Screen) -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("My Log", "Sign In")
                        onSuccess(Screen.Home)
                    } else {
                        task.exception?.let { exception ->
                            when (exception) {
                                is FirebaseAuthInvalidCredentialsException -> {
                                    Log.e("My Log", "Invalid password or email")
                                }
                                is FirebaseAuthInvalidUserException -> {
                                    Log.e("My Log", "User not found or disabled")
                                }
                                else -> {
                                    Log.e("My Log", "Sign in failed: ${exception.message}")
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("My Log", "Error: ${exception.message}")
                }
        } else {
            Log.e("My Log", "Email or password is empty")
        }
    }



    fun deleteAccount(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        val credential = EmailAuthProvider.getCredential(email,password)
        auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                auth.currentUser?.delete()?.addOnCompleteListener{ task ->
                    if (task.isSuccessful){

                    }else {

                    }
                }
            }else {

            }
        }

    }

}