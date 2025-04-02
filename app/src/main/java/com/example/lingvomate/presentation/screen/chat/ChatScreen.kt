package com.example.lingvomate.presentation.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingvomate.R
import com.example.lingvomate.model.Message
import com.example.lingvomate.presentation.navigation.Screen
import com.example.lingvomate.presentation.screen.state.ChatScreenEvent
import com.example.lingvomate.presentation.viewmodel.ChatViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ChatScreen(
    viewModel : ChatViewModel,
    onNavigateTo: (Screen) -> Unit,
    channelId: String
) {
    ChatView(
        onNavigateTo = onNavigateTo,
        onEvent = viewModel::onEvent,
        channelId = channelId,
        message = viewModel.message
    )
}

@Composable
fun ChatView(
    onNavigateTo: (Screen) -> Unit,
    onEvent: (ChatScreenEvent) -> Unit,
    channelId: String,
    message: StateFlow<List<Message>>
) {
    Scaffold {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            LaunchedEffect(key1 = true) {
                onEvent(ChatScreenEvent.ListenForMessages(channelId))
            }
            val messages = message.collectAsState()
            ChatMessages(
                messages = messages.value,
                onSendMessage = { message ->
                    onEvent(ChatScreenEvent.SendMessage(message = message,channelId = channelId))
                }
            )
        }
    }
}


@Composable
fun ChatMessages(
    messages : List<Message>,
    onSendMessage: (String) -> Unit
) {
    var message by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(messages) { message ->
                ChatBubble(message)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .background(color = Color(android.graphics.Color.LTGRAY))
        ){
            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(text = "Type a message")
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {

                    }
                )
            )
            IconButton(onClick = {
                onSendMessage(message)
                message = ""
            }){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "send message"
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid

    val bubbleColor = if (isCurrentUser) {
        R.color.purple_200
    }else {
        R.color.teal_200
    }

    Box(modifier = Modifier.fillMaxWidth().padding(
        vertical = 4.dp,
        horizontal = 8.dp
    )){
        val alignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
        Box(
            modifier = Modifier.padding(10.dp)
                .background(
                    color = colorResource(bubbleColor),
                    shape = RoundedCornerShape(10.dp)
                ).align(alignment)
        ) {
            Text(text = message.message, fontSize = 16.sp, modifier = Modifier.padding(start = 5.dp, top = 5.dp))
        }
    }
}