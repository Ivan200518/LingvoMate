package com.example.lingvomate.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.fillMaxWidth()) {
            FloatingActionButton(onClick = {

            }) {
                Icon(imageVector = Icons.Default.AccountCircle,
                    contentDescription = "profilePhoto")
            }
        }
    }
}