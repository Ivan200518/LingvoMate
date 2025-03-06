package com.example.lingvomate.presentation.screen.state

import com.google.firebase.auth.FirebaseAuth


sealed class RegisterScreenEvent {
    data class EmailUpdated(val newEmail: String) :RegisterScreenEvent()
    data class PasswordUpdated(val newPassword: String) : RegisterScreenEvent()
    data class ConfirmPasswordUpdated(val newConfirmPassword: String) : RegisterScreenEvent()
    data class CreateUser(val auth: FirebaseAuth) : RegisterScreenEvent()
    class ValidateEmail() : RegisterScreenEvent()
    class ValidatePassword() : RegisterScreenEvent()
    class DataValidate() : RegisterScreenEvent()
}

data class RegisterScreenState(
    var email : String = "",
    var password: String = "",
    var confirmPassword: String = ""
)