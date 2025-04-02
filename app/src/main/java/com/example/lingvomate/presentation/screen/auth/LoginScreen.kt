package com.example.lingvomate.presentation.screen.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import com.example.lingvomate.R
import com.example.lingvomate.presentation.navigation.Screen
import com.example.lingvomate.presentation.screen.state.LoginScreenEvent
import com.example.lingvomate.presentation.screen.state.LoginScreenState
import com.example.lingvomate.presentation.viewmodel.LoginViewModel
import com.example.lingvomate.presentation.ui.theme.Violet
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.remote.Stream

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginView(
//        onNavigateTo = {},
//        state = LoginScreenState(),
//        onEvent = {},
//        isAuthenticated = {false},
//        loginMessage = ""
//    )
//}

@Composable
fun LoginScreen(
    onNavigateTo: (Screen) -> Unit,
    viewModel: LoginViewModel
) {
    LoginView(
        onNavigateTo = onNavigateTo,
        onEvent = viewModel::onEvent,
        state = viewModel.state,
        loginMessage = viewModel.loginMessage
    )
}

@Composable
fun LoginView(
    onNavigateTo : (Screen) -> Unit = {},
    state : LoginScreenState = LoginScreenState(),
    onEvent : (LoginScreenEvent) -> Unit = {},
    loginMessage : String
 ) {
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.padding(start = 25.dp, top = 40.dp),
                onClick = {
                    onNavigateTo(Screen.Welcome)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Violet
                ),
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"

                )
            }
            Spacer(modifier = Modifier.width(140.dp))
        }
        Image(
            painter = painterResource(R.drawable.sitting),
            contentDescription = "sittingImage",
            modifier = Modifier
                .size(width = 188.82.dp, height = 229.dp)
                .graphicsLayer(
                    rotationZ = 3f
                )
                .offset(x = 227.dp, y = 84.dp)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 173.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.padding(start = 22.dp),
                    text = stringResource(R.string.welcome_login),
                    fontFamily = FontFamily(Font(R.font.popsemi)),
                    fontSize = 24.sp
                )
                Text(
                    modifier = Modifier.padding(start = 22.dp, top = 6.dp),
                    text = stringResource(R.string.login_text),
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(56.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(56.dp)  // Устанавливаем высоту для TextField
                        .padding(18.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            modifier = Modifier.width(310.dp),
                            shape = RoundedCornerShape(15.dp),
                            value = state.email,
                            onValueChange = { value ->
                                onEvent(LoginScreenEvent.EmailUpdated(value)) },
                            textStyle = TextStyle(fontSize = 20.sp),
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Email,
                                    contentDescription = "email icon")
                            },
                            placeholder = {
                                Text(text = stringResource(R.string.email_enter))
                            },
                            singleLine = true

                        )
                        Spacer(modifier = Modifier.height(35.dp))
                        OutlinedTextField(
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .width(310.dp),
                            value = state.password,
                            onValueChange = {
                                onEvent(LoginScreenEvent.PasswordUpdated(it))
                            },
                            textStyle = TextStyle(fontSize = 20.sp),
                            visualTransformation = if (passwordVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Lock,
                                    contentDescription = "password icon")
                            }, placeholder = {
                                Text(text = stringResource(R.string.password_enter))
                            }, singleLine = true,
                            trailingIcon = {
                                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                val description = if(passwordVisible) "Hide password" else "Show password"

                                IconButton(onClick = {
                                    passwordVisible = !passwordVisible
                                }) {
                                    Icon(imageVector = image, contentDescription = description)
                                }
                            }

                        )
                        Spacer(modifier = Modifier.height(70.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            onClick = {
                                onEvent(LoginScreenEvent.SignIn( onSuccess = onNavigateTo))
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = Violet
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.login_button),
                                fontSize = 20.sp
                            )
                        }
                        Text(
                            text = stringResource(R.string.to_register_screen),
                            modifier = Modifier
                                .clickable {
                                    onNavigateTo(Screen.Register)
                                }
                                .padding(top = 30.dp), fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedButton(modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onEvent(LoginScreenEvent.GoogleSignIn(context,onNavigateTo))
                                onNavigateTo(Screen.Home)
                            }) {
                            Image(
                                painter = painterResource(R.drawable.google_icon),
                                contentDescription = "googleIcon"
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(
                                text = "Sign-in with Google",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                    }

                }
            }
        }

    }

}


