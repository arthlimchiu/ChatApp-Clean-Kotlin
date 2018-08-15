package com.imakeanapp.domain.messages.repository

import com.imakeanapp.domain.messages.model.Message
import io.reactivex.Completable
import io.reactivex.Observable


interface MessagesRepository {

    fun sendMessage(message: Message): Completable

    fun getMessages(): Observable<List<Message>>
}