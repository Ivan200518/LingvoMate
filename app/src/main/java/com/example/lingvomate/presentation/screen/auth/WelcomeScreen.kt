package com.example.lingvomate.presentation.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingvomate.R
import com.example.lingvomate.presentation.navigation.Screen
import com.example.lingvomate.presentation.ui.theme.Violet

@Composable
fun WelcomeScreen(onNavigateTo : (Screen) -> Unit ) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(88.dp))
            Text(
                modifier = Modifier.padding(start = 30.dp),
                text = stringResource(R.string.welcome_title),
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.popbold))
            )
            Text(
                modifier = Modifier.padding(start = 30.dp),
                text = stringResource(R.string.everyone),
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.popbold))
            )
            Text(
                color = Violet,
                text = stringResource(R.string.app_name_with),
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.popbold)),
                modifier = Modifier.padding()
            )
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painterResource(R.drawable.friend),
                contentDescription = "friend",
                modifier = Modifier.size(350.dp)
            )
            Spacer(modifier = Modifier.height(70.dp))
            Button(modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 40.dp),onClick = {
                onNavigateTo(Screen.Login)
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Violet
            )){
                Text(
                    text = stringResource(R.string.start_welcome),
                    fontSize = 20.sp
                )
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun WelcomeScreePreview(){
    WelcomeScreen {

    }
}
