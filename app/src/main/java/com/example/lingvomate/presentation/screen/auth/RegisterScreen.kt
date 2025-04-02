package com.example.lingvomate.presentation.screen.auth

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lingvomate.R
import com.example.lingvomate.presentation.navigation.Screen
import com.example.lingvomate.presentation.screen.models.RegisterDataError
import com.example.lingvomate.presentation.screen.state.RegisterScreenEvent
import com.example.lingvomate.presentation.screen.state.RegisterScreenState
import com.example.lingvomate.presentation.viewmodel.RegisterViewModel
import com.example.lingvomate.presentation.ui.theme.Violet
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun RegisterScreen(
    onNavigateTo: (Screen) -> Unit
) {
    val viewModel = viewModel<RegisterViewModel>()
    val registerError = viewModel.registerDataError
    val isFormValid = viewModel.isFormValid
    RegisterView(
        onNavigateTo = onNavigateTo,
        onEvent = viewModel::onEvent,
        state = viewModel.state,
        registerDataError = registerError,
        isFormValid = isFormValid
    )
}

@Composable
fun RegisterView(
    onNavigateTo : (Screen) -> Unit = {},
    state : RegisterScreenState = RegisterScreenState(),
    registerDataError: RegisterDataError,
    onEvent : (RegisterScreenEvent) -> Unit = {},
    isFormValid : Boolean
) {
    val auth = Firebase.auth
    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.padding(start = 25.dp, top = 40.dp),
                onClick = {

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
                    text = stringResource(R.string.register_title),
                    fontFamily = FontFamily(Font(R.font.popsemi)),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(95.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(56.dp)  // Устанавливаем высоту для TextField
                        .padding(18.dp)
                ) {
                    Column {
                        OutlinedTextField(
                            modifier = Modifier.width(310.dp),
                            shape = RoundedCornerShape(15.dp),
                            value = state.email,
                            onValueChange = { value ->
                                onEvent(RegisterScreenEvent.EmailUpdated(value))
                                onEvent(RegisterScreenEvent.ValidateEmail()) },
                            textStyle = TextStyle(fontSize = 20.sp),
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Email,
                                    contentDescription = "email icon")
                            },
                            placeholder = {
                                Text(text = stringResource(R.string.email_enter))
                            } ,supportingText = {
                                Text(text = registerDataError.emailError, color = MaterialTheme.colorScheme.error)
                            }

                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .width(310.dp),
                            value = state.password,
                            onValueChange = {
                                onEvent(RegisterScreenEvent.PasswordUpdated(it))
                                onEvent(RegisterScreenEvent.ValidatePassword())
                            },
                            textStyle = TextStyle(fontSize = 20.sp),
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Lock,
                                    contentDescription = "password icon")
                            }, placeholder = {
                                Text(text = stringResource(R.string.password_enter))
                            }, singleLine = true,
                            supportingText = {
                                Text(text = registerDataError.passwordError, color = MaterialTheme.colorScheme.error)
                            }
                        )
                        OutlinedTextField(
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .width(310.dp).padding(top = 20.dp),
                            value = state.confirmPassword,
                            onValueChange = {
                                onEvent(RegisterScreenEvent.ConfirmPasswordUpdated(it))
                                onEvent(RegisterScreenEvent.ValidatePassword())
                            },
                            textStyle = TextStyle(fontSize = 20.sp),
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Lock,
                                    contentDescription = "password icon")
                            }, placeholder = {
                                Text(text = stringResource(R.string.confirm_password))
                            }, singleLine = true,
                            supportingText = {
                                if (state.password != state.confirmPassword) {
                                    Text(text = "Passwords don't match", color = MaterialTheme.colorScheme.error)
                                }
                            }

                        )
                        Spacer(modifier = Modifier.height(70.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .padding(end = 40.dp),
                            onClick = {
                                if (isFormValid) {
                                    onEvent(RegisterScreenEvent.CreateUser(auth))
                                    onNavigateTo(Screen.Confirm)
                                }
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = Violet
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.create_account),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    val error = RegisterDataError()
    RegisterView(
        onEvent = {},
        onNavigateTo = {},
        state = RegisterScreenState("","",""),
        registerDataError = error,
        isFormValid = false,
    )
}

