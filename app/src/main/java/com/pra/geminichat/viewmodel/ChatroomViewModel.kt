package com.pra.geminichat.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.pra.geminichat.model.ChatModel
import com.pra.geminichat.utils.ModelRoles
import kotlinx.coroutines.launch

class ChatroomViewModel : ViewModel() {

    val chatList by lazy {
        mutableStateListOf<ChatModel>()
    }


    val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")

    fun sendMessage(query: String) {
        viewModelScope.launch {
            chatList.add(ChatModel(message = query, role = ModelRoles.ROLE_USER))

            val response = model.generateContent(prompt = query)
            chatList.add(
                (ChatModel(
                    message = response.text.toString(),
                    role = ModelRoles.ROLE_MODEL
                ))
            )
        }
    }
}