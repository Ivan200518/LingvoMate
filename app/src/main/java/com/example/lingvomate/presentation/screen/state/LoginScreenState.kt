package com.example.lingvomate.presentation.screen.state

import android.content.Context
import coil.compose.AsyncImagePainter
import com.example.lingvomate.presentation.navigation.Screen

sealed class LoginScreenEvent{
    data class EmailUpdated(val newEmail: String) : LoginScreenEvent()
    data class PasswordUpdated(val newPassword: String) : LoginScreenEvent()
    class SignIn(val onSuccess: (Screen) -> Unit): LoginScreenEvent()
    data class GoogleSignIn(val context: Context, val onSuccess: (Screen) -> Unit) : LoginScreenEvent()

}

data class LoginScreenState(
    var email : String = "",
    var password : String = ""
)