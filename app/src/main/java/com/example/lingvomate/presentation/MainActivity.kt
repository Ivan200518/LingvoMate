package com.example.lingvomate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.lingvomate.presentation.navigation.MainNav
import com.example.lingvomate.presentation.viewmodel.LoginViewModel
import com.example.lingvomate.presentation.ui.theme.LingvoMateTheme
import com.example.lingvomate.presentation.viewmodel.ChatViewModel
import com.example.lingvomate.presentation.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.getViewModel

private lateinit var myAuth : FirebaseAuth
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        myAuth = FirebaseAuth.getInstance()
        setContent {
            LingvoMateTheme {
                val viewModel = getViewModel<LoginViewModel>()
                val homeViewModel = getViewModel<HomeViewModel>()
                val chatViewModel = getViewModel<ChatViewModel>()
                MainNav(
                    navHostController = rememberNavController(),
                    loginViewModel = viewModel,
                    homeViewModel = homeViewModel,
                    chatViewModel = chatViewModel
                )
            }
        }
    }
}

