package com.example.lingvomate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lingvomate.model.Message
import com.example.lingvomate.presentation.screen.state.ChatScreenEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ChatViewModel: ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val message = _messages.asStateFlow()
    private val db = Firebase.database

    fun onEvent(event : ChatScreenEvent) {
        return when(event) {
            is ChatScreenEvent.SendMessage -> sendMessage(event.channelId,event.message)
            is ChatScreenEvent.ListenForMessages -> listenForMessages(event.channelId)
        }
    }

    fun sendMessage(channelId: String,messageText: String) {
        val message = Message(
            id = db.reference.push().key ?: UUID.randomUUID().toString(),
            senderId = Firebase.auth.currentUser?.uid ?: "",
            message = messageText,
            createAt = System.currentTimeMillis(),
            senderName = Firebase.auth.currentUser?.displayName ?: "",
            imageUrl = null,

        )
        db.getReference("messages").child(channelId).push().setValue(message)
    }

    fun listenForMessages(channelId: String) {
        db.getReference("messages").child(channelId).orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Message>()
                    snapshot.children.forEach{ data->
                        val message = data.getValue(Message::class.java)
                        message?.let{
                            list.add(it)
                        }

                    }
                    _messages.value = list
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }

            )
    }
}