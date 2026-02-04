package com.pra.geminichat.view

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pra.geminichat.model.ChatModel
import com.pra.geminichat.ui.theme.Purple80
import com.pra.geminichat.ui.theme.PurpleGrey80
import com.pra.geminichat.utils.ModelRoles
import com.pra.geminichat.viewmodel.ChatroomViewModel

@Composable
fun ChatRoom(modifier: Modifier = Modifier, viewModel: ChatroomViewModel) {
    Column(modifier = modifier) {
        AppHeader()
        ChatList(modifier = Modifier.weight(1f), chatList = viewModel.chatList)
        MessageInput(
            onMessageSend = {
                viewModel.sendMessage(query = it)
            })
    }
}

@Composable
fun ChatList(modifier: Modifier = Modifier, chatList: List<ChatModel>) {
    LazyColumn(
        modifier = modifier, reverseLayout = true
    ) {
        items(chatList.reversed()) {
            ChatRow(chat = it)
        }
    }
}

@Composable
fun ChatRow(chat: ChatModel) {
    val isUser = chat.role == ModelRoles.ROLE_USER

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(
                        if (isUser) Alignment.BottomEnd else Alignment.BottomStart
                    )
                    .padding(
                        start = if (isUser) 70.dp else 8.dp,
                        end = if (isUser) 8.dp else 70.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .background(
                        color = if (isUser) Purple80 else PurpleGrey80,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)

            ) {
                Text(
                    text = chat.message, fontWeight = FontWeight.W500
                )
            }
        }
    }

}

@Composable
fun AppHeader(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            modifier = modifier.padding(16.dp),
            text = "Gemini Chat",
            color = Color.White,
            fontSize = 22.sp
        )
    }
}

@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {

    var message by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f), value = message, onValueChange = {
                message = it
            })

        IconButton(onClick = {
            onMessageSend(message)
            message = ""
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send"
            )
        }
    }
}