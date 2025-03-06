package com.example.lingvomate.presentation.viewmodel

import GoogleAuthClient
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lingvomate.presentation.screen.state.LoginScreenEvent
import com.example.lingvomate.presentation.screen.state.LoginScreenState
import com.google.api.Authentication
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginViewModel(private val googleAuthClient:  GoogleAuthClient) : ViewModel() {
    var state by mutableStateOf(LoginScreenState())
    var isAuthenticated by mutableStateOf(googleAuthClient.isSingedIn())
    var loginMessage by mutableStateOf("")
    private  val auth = FirebaseAuth.getInstance()
    fun onEvent(event : LoginScreenEvent)  {
        when(event) {
            is LoginScreenEvent.EmailUpdated -> this.state = state.copy(email = event.newEmail)
            is LoginScreenEvent.PasswordUpdated -> this.state = state.copy(password = event.newPassword)
            is LoginScreenEvent.SignIn -> loginWithEmail(state.email,state.password)
            is LoginScreenEvent.GoogleSignIn ->viewModelScope.launch {
                googleAuthClient.signIn()
            }
        }
    }



    fun loginWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null && user.isEmailVerified) {
                                isAuthenticated = true
                            } else {
                                loginMessage = "Email не подтвержден. Проверьте почту."
                                auth.signOut()

                            }
                        } else {
                            loginMessage = "Ошибка входа: ${task.exception?.localizedMessage}"
                        }
                    }
            }
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