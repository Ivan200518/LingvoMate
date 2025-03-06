package com.example.lingvomate.presentation.screen.state


sealed class ChatScreenEvent {
    data class SendMessage(val message:String, val channelId: String) :ChatScreenEvent()
    data class  ListenForMessages(val channelId:String) : ChatScreenEvent()
}
