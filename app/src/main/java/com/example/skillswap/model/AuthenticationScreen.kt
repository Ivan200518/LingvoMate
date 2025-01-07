package com.example.skillswap.model

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillswap.R
import com.example.skillswap.ui.theme.violet
import com.example.skillswap.ui.theme.violet2


@Composable
fun AuthenticationScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "SkillSwap",
            fontSize = 50.sp,
            modifier = Modifier.padding(top = 100.dp),
        )
        Row(modifier = Modifier.padding(top = 50.dp), horizontalArrangement = Arrangement.SpaceEvenly )
        {
            Text(text = "Create account ")
            Text(text = "Log in")
        }
        Column(modifier = Modifier.padding(30.dp)) {
            val textState = remember { mutableStateOf("") }

            TextField(

                value = textState.value,
                onValueChange = { text ->
                    textState.value = text
                },
                label = {
//                    Icon(imageVector = Icons.Filled.Email, contentDescription = "email")
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Image(painter = painterResource(R.drawable.ic_email), contentDescription = "email")
                        Text("Email") }
                    },
                shape = RoundedCornerShape(15.dp)
            )

            TextField(
                modifier = Modifier.padding(top = 15.dp),
                value = textState.value,
                onValueChange = { text ->
                    textState.value = text
                },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Image(painter = painterResource(R.drawable.ic_password), contentDescription = "password")
                        Text("Password") }
                    },
                shape = RoundedCornerShape(15.dp),
            )

        }
        Text(
            text = "Forgot password?",
            modifier = Modifier.padding(30.dp)
        )




    }
}
@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    val passwordState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "SkillSwap",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround){
                TextButton(onClick = {

                }) {
                    Text(text = "Create Account")
                }
                TextButton(onClick = {

                }) {
                    Text(text = "Log in")
                }
            }
            TextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                label = { Text("Электронная почта") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Пароль") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Забыли пароль?")
            }
            Spacer(modifier = Modifier.height(300.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { /* Логика входа через Facebook */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Facebook"
                    )
                }
                IconButton(onClick = { /* Логика входа через Twitter */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_twitter),
                        contentDescription = "Twitter"
                    )
                }
                IconButton(onClick = { /* Логика входа через Google */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google"
                    )
                }
                IconButton(onClick = { /* Логика входа через VK */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_twitter),
                        contentDescription = "VK"
                    )
                }
            }
        }
    }
}