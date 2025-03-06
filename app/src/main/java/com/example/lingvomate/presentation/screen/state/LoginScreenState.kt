package com.example.lingvomate.presentation.screen.state

import android.content.Context

sealed class LoginScreenEvent{
    data class EmailUpdated(val newEmail: String) : LoginScreenEvent()
    data class PasswordUpdated(val newPassword: String) : LoginScreenEvent()
    class SignIn : LoginScreenEvent()
    data class GoogleSignIn(val context: Context) : LoginScreenEvent()

}

data class LoginScreenState(
    var email : String = "",
    var password : String = ""
)