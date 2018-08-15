package com.imakeanapp.domain.messages.usecase

import com.imakeanapp.domain.core.CompletableWithParamUseCase
import com.imakeanapp.domain.messages.model.Message
import com.imakeanapp.domain.messages.repository.MessagesRepository
import io.reactivex.Completable

class SendMessageUseCase(private val repository: MessagesRepository) : CompletableWithParamUseCase<Message> {

    override fun execute(t: Message): Completable {
        return repository.sendMessage(t)
    }
}