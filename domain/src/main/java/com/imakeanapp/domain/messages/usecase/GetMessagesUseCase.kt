package com.imakeanapp.domain.messages.usecase

import com.imakeanapp.domain.core.ObservableUseCase
import com.imakeanapp.domain.messages.model.Message
import com.imakeanapp.domain.messages.repository.MessagesRepository
import io.reactivex.Observable

class GetMessagesUseCase(private val repository: MessagesRepository) : ObservableUseCase<List<Message>> {

    override fun execute() = repository.getMessages()
}