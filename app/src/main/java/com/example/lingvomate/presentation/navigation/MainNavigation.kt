package com.example.lingvomate.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lingvomate.presentation.screen.auth.LoginScreen
import com.example.lingvomate.presentation.screen.ConfirmEmailScreen
import com.example.lingvomate.presentation.screen.HomeScreen
import com.example.lingvomate.presentation.screen.ProfileScreen
import com.example.lingvomate.presentation.screen.SearchScreen
import com.example.lingvomate.presentation.screen.auth.RegisterScreen
import com.example.lingvomate.presentation.screen.auth.WelcomeScreen
import com.example.lingvomate.presentation.screen.chat.ChatScreen
import com.example.lingvomate.presentation.viewmodel.ChatViewModel
import com.example.lingvomate.presentation.viewmodel.HomeViewModel
import com.example.lingvomate.presentation.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    abstract val route: String

    @Serializable
    data object Login : Screen() {
        override val route: String = "login"
    }

    @Serializable
    data object Register : Screen() {
        override val route: String = "register"
    }

    @Serializable
    data object Welcome : Screen() {
        override val route: String = "welcome"
    }

    @Serializable
    data object Home : Screen() {
        override val route: String = "home"
    }

    @Serializable
    data object Confirm : Screen() {
        override val route: String = "confirm"
    }

    @Serializable
    data object Chat : Screen() {
        override val route: String = "chat"
    }

    @Serializable
   data object Search : Screen() {
        override val route: String = "search"
    }

    @Serializable
    data object Profile : Screen() {
        override val route: String = "profile"
    }
}
@Composable
fun MainNav(
    modifier: Modifier = Modifier,
    navHostController : NavHostController,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    chatViewModel: ChatViewModel) {

    // Автоматическая навигация при изменении статуса входа
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

        composable<Screen.Search> {
            SearchScreen()
        }
        composable<Screen.Profile> {
            ProfileScreen()
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

@Composable
fun MainApp(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    chatViewModel: ChatViewModel
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomNavigation = remember(currentRoute) {
        val routeName = currentRoute?.substringAfterLast('.')?.lowercase()
        bottomNavigationScreens.any { it.route == routeName}
    }
    Scaffold(
        bottomBar = {
            if (showBottomNavigation) {
                BottomNavigation(navHostController)
            }
        }
    ) { paddingValues ->
        MainNav(
            modifier = Modifier.padding(paddingValues),
            navHostController = navHostController,
            loginViewModel = loginViewModel,
            homeViewModel = homeViewModel,
            chatViewModel = chatViewModel
        )
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    NavigationBar {
        bottomNavigationScreens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screen.Home -> Icons.Outlined.Home
                            Screen.Search -> Icons.Outlined.Search
                            Screen.Profile -> Icons.Outlined.Person
                            else -> throw IllegalArgumentException("Unknown screen")
                        },
                        contentDescription = screen.route
                    )
                },
                label = { Text(text = when (screen) {
                    Screen.Home -> "Home"
                    Screen.Search -> "Search"
                    Screen.Profile -> "Profile"
                    else -> throw IllegalArgumentException("Unknown screen")
                }) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

val bottomNavigationScreens = listOf(
    Screen.Home,
    Screen.Search,
    Screen.Profile
)