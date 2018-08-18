package com.imakeanapp.chatappkotlin.messages.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.imakeanapp.domain.messages.usecase.GetMessagesUseCase
import com.imakeanapp.domain.messages.usecase.SendMessageUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class MessagesViewModelFactory @Inject constructor(
        private val getMessages: GetMessagesUseCase,
        private val sendMessage: SendMessageUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
            return MessagesViewModel(getMessages, sendMessage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}