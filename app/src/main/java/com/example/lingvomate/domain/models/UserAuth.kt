package com.example.lingvomate.domain.models

class UserAuth {
    fun execute(email :String,
                password : String) : UserData{
        return UserData(email,password)
    }
}