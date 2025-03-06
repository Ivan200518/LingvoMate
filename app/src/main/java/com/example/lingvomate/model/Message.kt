package com.example.lingvomate.model

import android.content.BroadcastReceiver

data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val createAt: Long = System.currentTimeMillis(),
    val senderName: String? = null,
    val imageUrl: String? = null
)