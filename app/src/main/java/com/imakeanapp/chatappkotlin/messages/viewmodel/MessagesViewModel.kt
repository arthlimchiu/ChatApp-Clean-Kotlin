package com.imakeanapp.chatappkotlin.messages.viewmodel

import android.arch.lifecycle.ViewModel
import com.imakeanapp.domain.messages.model.Message
import com.imakeanapp.domain.messages.usecase.GetMessagesUseCase
import com.imakeanapp.domain.messages.usecase.SendMessageUseCase
import io.reactivex.Completable
import io.reactivex.Observable

class MessagesViewModel(private val getMessages: GetMessagesUseCase,
                        private val sendMessage: SendMessageUseCase) : ViewModel() {

    fun sendMessage(message: Message) = sendMessage.execute(message)

    fun getMessages() = getMessages.execute()
}