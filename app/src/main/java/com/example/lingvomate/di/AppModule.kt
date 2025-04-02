package com.example.lingvomate.di

import GoogleAuthClient
import com.example.lingvomate.presentation.viewmodel.ChatViewModel
import com.example.lingvomate.presentation.viewmodel.HomeViewModel
import com.example.lingvomate.presentation.viewmodel.LoginViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val appModule = module {
    single<GoogleAuthClient> { ( GoogleAuthClient(androidApplication())) }
    viewModel<LoginViewModel>{
        LoginViewModel(get())
    }
    viewModel<HomeViewModel>{
        HomeViewModel(get())
    }
    viewModel<ChatViewModel>{
        ChatViewModel()
    }



}