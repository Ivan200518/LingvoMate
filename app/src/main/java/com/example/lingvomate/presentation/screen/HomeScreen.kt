package com.example.lingvomate.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lingvomate.model.Channel
import com.example.lingvomate.presentation.navigation.Screen
import com.example.lingvomate.presentation.screen.state.HomeScreenEvent
import com.example.lingvomate.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.StateFlow


//@Composable
//@Preview(showBackground = true)
//fun HomePreview() {
//    HomeView(
//        onNavigateTo = {},
//        onEvent =  {},
//    )
//}

@Composable
fun HomeScreen(
    onNavigateTo :(Screen) -> Unit,
    viewModel: HomeViewModel
)   {
    HomeView(
        onNavigateTo = onNavigateTo,
        onEvent = viewModel::onEvent,
        channels = viewModel.channels
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    onNavigateTo:(Screen) -> Unit,
    onEvent: (HomeScreenEvent) -> Unit,
    channels: StateFlow<List<Channel>>,
) {
    val addChannel = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()


    Scaffold (floatingActionButton = {
        Box(
            modifier = Modifier
                .padding(bottom = 46.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Cyan)
                .clickable {
                    addChannel.value = true
                }
        ){
            Text("Add Channel", modifier = Modifier.padding(16.dp))
        }
    }, ){
        Column(modifier = Modifier.padding(it).fillMaxSize()) {
            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(HomeScreenEvent.SignOut())
                    onNavigateTo(Screen.Login)
                }) {
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "Sign out",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            LazyColumn {
                items(channels.value) { channel ->
                    Column {
                        Text(text =  channel.name
                            , modifier = Modifier.fillMaxWidth().padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Cyan.copy(alpha = 0.3f))
                                .clickable {
                                    onNavigateTo(Screen.Chat)
                                }.padding(16.dp))
                    }
                }

            }
            Spacer(modifier = Modifier.height(70.dp))


        }
    }
    if (addChannel.value) {
        ModalBottomSheet(onDismissRequest = {

        }, sheetState = sheetState ) {
            AddChannelDialog {
                onEvent(HomeScreenEvent.AddChannel(it))
                addChannel.value = false
            }
        }
    }
}



@Composable
fun AddChannelDialog(onAddChannel: (String) -> Unit) {
    val channelName = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Add Channel")
        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = channelName.value,
            onValueChange = {
                channelName.value = it
            }, label = {
                Text(text = "Channel Name")
            }, singleLine = true
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {
            onAddChannel(channelName.value)
        }, modifier = Modifier.fillMaxWidth()) {
              Text(text = "Add")
        }
    }
}