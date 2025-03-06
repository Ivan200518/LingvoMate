package com.example.lingvomate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lingvomate.presentation.screen.auth.LoginScreen
import com.example.lingvomate.presentation.screen.ConfirmEmailScreen
import com.example.lingvomate.presentation.screen.HomeScreen
import com.example.lingvomate.presentation.screen.auth.RegisterScreen
import com.example.lingvomate.presentation.screen.auth.WelcomeScreen
import com.example.lingvomate.presentation.screen.chat.ChatScreen
import com.example.lingvomate.presentation.viewmodel.ChatViewModel
import com.example.lingvomate.presentation.viewmodel.HomeViewModel
import com.example.lingvomate.presentation.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable


sealed class Screen {
    @Serializable
    data object  Login : Screen()
    @Serializable
    data object  Register : Screen()
    @Serializable
    data object  Welcome: Screen()
    @Serializable
    data object  Home: Screen()
    @Serializable
    data object  Confirm: Screen()
    @Serializable
    data object  Chat: Screen()
}
@Composable
fun MainNav(
    modifier: Modifier = Modifier,
    navHostController : NavHostController,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    chatViewModel: ChatViewModel) {
    NavHost(
        startDestination = firstScreen(),
        navController = navHostController,
        modifier = modifier
    ) {
        composable<Screen.Login> {
            LoginScreen (
                onNavigateTo = { navigateTo ->
                    navHostController.navigate(navigateTo)
                },loginViewModel
            )
        }
        composable<Screen.Chat> {
            val channelId = it.arguments?.getString("channelId") ?: ""
            ChatScreen(
                onNavigateTo = { navigateTo ->
                    navHostController.navigate(navigateTo)
                }, viewModel = chatViewModel,
                channelId = channelId
            )
        }
        composable<Screen.Register> {
            RegisterScreen (onNavigateTo = { navigateTo ->
                navHostController.navigate(navigateTo)
            })
        }
        composable<Screen.Welcome> {
            WelcomeScreen(onNavigateTo = { navigateTo ->
                navHostController.navigate((navigateTo))
            })
        }
        composable<Screen.Home> {
            HomeScreen(onNavigateTo = { navigateTo ->
                navHostController.navigate((navigateTo))
            },homeViewModel)
        }
        composable<Screen.Confirm> {
            ConfirmEmailScreen()
        }
    }
}

fun firstScreen() : Screen{
    val auth = FirebaseAuth.getInstance()
    return if (auth.currentUser != null) {
        Screen.Home
    }else {
        Screen.Welcome
    }
}