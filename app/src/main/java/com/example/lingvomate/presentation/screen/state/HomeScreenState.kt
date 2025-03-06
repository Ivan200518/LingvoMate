package com.example.lingvomate.presentation.screen.state


sealed class HomeScreenEvent() {
    class SignOut() : HomeScreenEvent()
    data class AddChannel(val name: String) : HomeScreenEvent()
}


class HomeScreenState {

}