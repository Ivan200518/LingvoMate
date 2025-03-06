package com.example.lingvomate.presentation.viewmodel

import GoogleAuthClient
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lingvomate.model.Channel
import com.example.lingvomate.presentation.screen.state.HomeScreenEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val googleAuthClient: GoogleAuthClient) : ViewModel() {
    val auth = FirebaseAuth.getInstance()

    private val firebaseDatabase = Firebase.database
    init {
        getChannels()
    }

    private val _channels  = MutableStateFlow<List<Channel>>(emptyList())

    val channels = _channels.asStateFlow()

    private fun getChannels() {
        firebaseDatabase.getReference("channel").get().addOnSuccessListener {
            val list = mutableListOf<Channel>()
            it.children.forEach{ data ->
                val channel = Channel(data.key!!, data.value.toString())
                list.add(channel)
            }
            _channels.value = list
        }
    }
    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.SignOut -> viewModelScope.launch {
                googleAuthClient.signOut()
            }

            is HomeScreenEvent.AddChannel -> addChannel(event.name)
        }
    }
    private fun signOutUser() {
        auth.signOut()
        Log.d("My Log", "Sign Out")
    }

    private fun addChannel(name : String) {
        val key = firebaseDatabase.getReference("channel").push().key
        firebaseDatabase.getReference("channel").child(key!!).setValue(name).addOnSuccessListener {
            getChannels()
        }
    }

}